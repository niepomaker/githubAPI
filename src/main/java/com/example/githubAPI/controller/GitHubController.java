package com.example.githubAPI.controller;

import com.example.githubAPI.service.GitHubService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/users/{username}/repositories")
    public String getRepositories(@PathVariable String username, @RequestHeader(value = "Accept") String acceptHeader) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        if (gitHubService.getRepositories(username, acceptHeader).isPresent()){
            if (gitHubService.getRepositories(username, acceptHeader).get().findValue("message") != null){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Status", "404");
                jsonObject.put("Message", "User does not exist!");
                jsonArray.put(jsonObject);
                return jsonArray.toString();
            }
            for (int i = 0; i < gitHubService.getRepositories(username, acceptHeader).get().size(); i++) {
                JSONObject jsonObject = new JSONObject();
                JSONArray branchesArray = new JSONArray();
                jsonObject.put("Name", gitHubService.getRepositories(username, acceptHeader).get().path(i).get("name").asText());
                jsonObject.put("Owner", gitHubService.getRepositories(username, acceptHeader).get().path(i).get("owner").get("login").asText());
                for (int j = 0; j < gitHubService.getBranches(username, gitHubService.getRepositories(username, acceptHeader).get().path(i).get("name").asText()).size(); j++) {
                    JSONObject branch = new JSONObject();
                    branch.put("Last commit", gitHubService.getBranches(username, gitHubService.getRepositories(username, acceptHeader).get().path(i).get("name").asText()).path(j).get("commit").get("sha").asText());
                    branch.put("Name", gitHubService.getBranches(username, gitHubService.getRepositories(username,acceptHeader).get().path(i).get("name").asText()).path(j).get("name").asText());
                    branchesArray.put(branch);
                }
                jsonObject.put("Branches", branchesArray);
                jsonArray.put(jsonObject);
            }

            return jsonArray.toString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Status", "406");
            jsonObject.put("Message", "Wrong header! Please provide application/json header. ");
            jsonArray.put(jsonObject);
            return jsonArray.toString();
        }
    }
}




