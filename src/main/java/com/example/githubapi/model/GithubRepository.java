package com.example.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record GithubRepository(
        @JsonProperty("name") String name,
        @JsonProperty("owner") Owner owner,
        List<Branch> branches
) {}

record Owner(@JsonProperty("login") String login) {}
