package com.docterryome.kroger.krogerapicart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "kroger.client")
@Getter
@Setter
public class KrogerConfig {
    private String id;
    private String secret;
}
