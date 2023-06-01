package com.example.githubAPI.service;

import com.example.githubAPI.entity.GitHubRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.apache.http.impl.client.HttpClients;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class GitHubService {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GitHubService() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode getRepositories(String username) throws IOException {
        String url = "https://api.github.com/users/" + username + "/repos";
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/vnd.github.v3+json");

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        return objectMapper.readTree(inputStream);
    }
}
