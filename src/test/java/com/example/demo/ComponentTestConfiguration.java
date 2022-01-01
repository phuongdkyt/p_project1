package com.example.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Import(SpringTransactionsProcessEngineConfiguration.class)
//public class ComponentTestConfiguration extends StandaloneProcessEngineConfiguration {

@Configuration
@EnableWebMvc
@ComponentScan("com.example.demo.config")
//@Profile("test")
public class ComponentTestConfiguration {

}
