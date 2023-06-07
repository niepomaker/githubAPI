package com.example.githubAPI.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GitHubService() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public Optional<JsonNode> getRepositories(String username, String header) throws IOException {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        HttpGet request = new HttpGet(url);
        //Using GH TOKEN to increase the rate limit when using the REST API for my own purposes.
        request.setHeader("Authorization", "token {{GH_TOKEN}}");
        if (header.equals("application/xml")){
            return Optional.empty();
        }
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        return Optional.ofNullable(objectMapper.readTree(inputStream));
    }

    public JsonNode getBranches(String username, String repoName) throws IOException {
        String url = GITHUB_API_URL + "/repos/" + username + "/" + repoName + "/branches";
        HttpGet request = new HttpGet(url);
        //Using GH TOKEN to increase the rate limit when using the REST API for my own purposes.
        request.setHeader("Authorization", "token {{GH_TOKEN}}");

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        return objectMapper.readTree(inputStream);
    }
}
