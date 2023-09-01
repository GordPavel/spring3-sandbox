package com.example.spring3.config

import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LiquibaseConfig {

    @Bean
    @ConfigurationProperties("datasource.main-db")
    fun mainDataSource(): HikariDataSource = DataSourceBuilder
        .create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean
    fun liquibase(): SpringLiquibase = SpringLiquibase().apply {
        changeLog = "classpath:liquibase/master.xml"
        dataSource = mainDataSource()
    }

}