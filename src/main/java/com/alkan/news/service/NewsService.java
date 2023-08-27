package com.alkan.news.service;

import com.alkan.news.response.PythonApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    private final static RestTemplate restTemplate = new RestTemplate();
    private final static String pythonApiUrl = "http://localhost:5000/recommend";

    public static ResponseEntity<String> getRecommendations(String headline) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"headline\": \""+ headline +"\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(pythonApiUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String recommendation = response.getBody();
            System.out.println("Recommendation: " + recommendation);
            return ResponseEntity.ok(recommendation);
        } else {
            System.out.println("Request failed. Status code: " + response.getStatusCodeValue());
            return ResponseEntity.status(response.getStatusCode()).build();
        }

    }
}
