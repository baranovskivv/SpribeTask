package com.spribe.testTaskForSeniorJavaDeveloper.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EnvironmentUtil {

    private static final String APP_ID = "APP_ID";

    private final Environment env;

    private String appId;

    @PostConstruct
    public void init() {
        appId = env.getProperty(APP_ID);
    }

    public String getAppId(){
        return appId;
    }

}