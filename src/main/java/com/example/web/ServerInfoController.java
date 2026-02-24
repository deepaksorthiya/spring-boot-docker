package com.example.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API for Getting Full Server Info of hosted server
 */
@RestController
public class ServerInfoController {

    @GetMapping(value = {"/", "/server-info"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRequestInfo(@RequestHeader LinkedHashMap<String, String> httpHeaders, HttpServletRequest httpServletRequest) {
        httpHeaders.put("remoteHost", httpServletRequest.getRemoteHost());
        httpHeaders.put("localAddress", httpServletRequest.getLocalAddr());
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            httpHeaders.put("hostName", localHost.getHostName());
            httpHeaders.put("hostAddress", localHost.getHostAddress());
            httpHeaders.put("canonicalHostName", localHost.getCanonicalHostName());
            httpHeaders.put("serverLocalDateTime", LocalDateTime.now().toString());
            httpHeaders.put("serverZonedDateTime", ZonedDateTime.now().toString());
            httpHeaders.put("serverOffsetDateTime", OffsetDateTime.now().toString());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return httpHeaders;
    }
}
