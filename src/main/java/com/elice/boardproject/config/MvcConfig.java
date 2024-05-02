package com.elice.boardproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = "file:" + Paths.get("uploaded-images").toAbsolutePath().toString().replace("\\","/") + "/";
        registry.addResourceHandler("/uploaded-images/**").addResourceLocations(uploadDir);
    }
}