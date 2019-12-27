package com.cif.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class StatusExecutorConfiguration {

    @Bean
    public ExecutorService statusExecutor() {
        return Executors.newSingleThreadExecutor();
    }

}
