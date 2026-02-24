package com.example.web;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This will expose Spring Version info
 * under /actuator/info endpoint
 */
@Component
public class SpringBootVersionInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> map = new HashMap<>();
        map.put("framework-version", SpringVersion.getVersion());
        map.put("boot-version", SpringBootVersion.getVersion());

        builder.withDetail("spring", Collections.unmodifiableMap(map));
    }
}