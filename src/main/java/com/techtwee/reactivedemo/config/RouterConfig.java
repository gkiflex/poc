package com.techtwee.reactivedemo.config;

import com.techtwee.reactivedemo.handlers.CustomerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    public RouterFunction<ServerResponse> route(){
        return RouterFunctions.route()
                .GET("/router/customers/stream", customerHandler::getCustomerStream)
                .build();
    }
}
