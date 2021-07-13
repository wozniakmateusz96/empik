package com.exercise.empik.webclient.github;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class GithubClient {
    private static final String API_URL = "https://api.github.com/users/";
    public static final String USER_NOT_FOUND = "User not found";
    private final RestTemplate restTemplate = new RestTemplate();

    public GithubDto getUser(String login) {
        try {
            return restTemplate.getForObject(API_URL + login, GithubDto.class);
        } catch (HttpClientErrorException e) {
            return GithubDto.builder().errorMessage(USER_NOT_FOUND).build();
        }

    }
}
