package com.example.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Branch(
        @JsonProperty("name") String name,
        @JsonProperty("commit") Commit commit
) {}

record Commit(@JsonProperty("sha") String sha) {}