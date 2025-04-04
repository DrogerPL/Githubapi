package com.example.githubapi.controller;

import com.example.githubapi.exception.GithubUserNotFoundException;
import com.example.githubapi.model.GithubRepository;
import com.example.githubapi.service.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<GithubRepository>> getNonForkRepositories(@PathVariable String username) {
        try {
            List<GithubRepository> repositories = githubService.getNonForkRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (GithubUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

