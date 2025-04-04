package com.example.githubapi.service;

import com.example.githubapi.exception.GithubUserNotFoundException;
import com.example.githubapi.model.Branch;
import com.example.githubapi.model.GithubRepository;
import com.example.githubapi.model.GithubRepositoryDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;

@Service
public class GithubService {

    private final RestClient restClient;

    public GithubService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.github.com")
                .build();
    }

    public List<GithubRepository> getNonForkRepositories(String username) {
        try {
            var repositories = restClient.get()
                    .uri("/users/{username}/repos", username)
                    .retrieve()
                    .body(GithubRepository[].class);

            if (repositories == null || repositories.length == 0) {
                throw new GithubUserNotFoundException(username);
            }

            return List.of(repositories).stream()
                    .filter(repo -> repo != null && repo.owner() != null)
                    .filter(repo -> !isFork(username, repo.name()))
                    .map(repo -> new GithubRepository(
                            repo.name(),
                            repo.owner(),
                            getBranches(username, repo.name())
                    ))
                    .toList();
        } catch (HttpClientErrorException.NotFound e) {
            throw new GithubUserNotFoundException(username);
        }
    }


    private boolean isFork(String username, String repoName) {
        var repoDetails = restClient.get()
                .uri("/repos/{username}/{repoName}", username, repoName)
                .retrieve()
                .body(GithubRepositoryDetails.class);

        return repoDetails != null && repoDetails.isFork();
    }


    private List<Branch> getBranches(String username, String repoName) {
        var branches = restClient.get()
                .uri("/repos/{username}/{repoName}/branches", username, repoName)
                .retrieve()
                .body(Branch[].class);

        return branches != null ? List.of(branches) : List.of();
    }
}