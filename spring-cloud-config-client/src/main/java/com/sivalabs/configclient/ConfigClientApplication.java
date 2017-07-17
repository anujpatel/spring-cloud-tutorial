package com.sivalabs.configclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@SpringBootApplication
@EnableConfigurationProperties({DataSourceProperties.class})
public class ConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}

	@Autowired
    DataSourceProperties dataSourceProperties;

	@RefreshScope
	@Bean
	public DataSource dataSource()
    {
        final org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        return dataSource;
    }
}

@RestController
class DemoController
{

	@Value("${spring.datasource.url}")
	private String jdbcUrl;

    @Autowired
    DataSourceProperties dataSourceProperties;

	@GetMapping("/jdbcUrl")
	public String jdbcUrl() {
        return jdbcUrl;
	}

    @GetMapping("/dataSourceProperties")
    public DataSourceProperties dataSourceProperties() {
        return dataSourceProperties;
    }
}

