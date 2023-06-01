package com.example.githubAPI.controller;

import com.example.githubAPI.entity.GitHubRepository;
import com.example.githubAPI.service.GitHubService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/users/{username}/repositories")
    public List<Map<String, String>> getRepositories(@PathVariable String username) throws IOException {
        List<Map<String, String>> repos = new ArrayList<Map<String, String>>();
        for (int i = 1; i < gitHubService.getRepositories(username).size(); i++){
            Map<String, String> map = new HashMap<>();
            map.put("name", gitHubService.getRepositories(username).path(i).get("name").asText());
            map.put("owner", gitHubService.getRepositories(username).path(i).get("owner").asText());
            repos.add(map);
        }
        return repos;
    }
}