package com.example.web;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
public class RestApiController {

    private final Logger logger = LoggerFactory.getLogger(RestApiController.class);
    private final RestClient restClient;

    public RestApiController(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @GetMapping(value = {"/", "/server-info"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequestInfo(@RequestHeader Map<String, String> httpHeaders, HttpServletRequest httpServletRequest) {
        httpHeaders.put("remoteHost", httpServletRequest.getRemoteHost());
        httpHeaders.put("localAddress", httpServletRequest.getLocalAddr());
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            httpHeaders.put("hostName", localHost.getHostName());
            httpHeaders.put("hostAddress", localHost.getHostAddress());
            httpHeaders.put("canonicalHostName", localHost.getCanonicalHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        logger.info("request headers :: {}", httpHeaders);
        return httpHeaders;
    }

    @GetMapping(value = {"/rest-client"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> restClientTest() {
        return restClient
                .get()
                .uri("https://jsonplaceholder.typicode.com/posts/{postId}/comments", 1)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    @GetMapping(value = {"/rest-client-error"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> restClientTestError() {
        return restClient
                .get()
                .uri("http://localhost:8080/404")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .toEntity(Object.class);
    }

    @GetMapping(value = {"/rest-server-error"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> restClientServerError() {
        return restClient
                .get()
                .uri("https://httpbin.org/status/503")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .toEntity(Object.class);
    }

    @GetMapping(value = {"/rest-client-delay"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> restClientTestTimeout() {
        return restClient
                .get()
                .uri("https://httpbin.org/delay/6")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    System.out.println(response.getStatusText());
                })
                .toEntity(Object.class);
    }
}
