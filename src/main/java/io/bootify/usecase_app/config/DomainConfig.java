package io.bootify.usecase_app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.bootify.usecase_app.domain")
@EnableJpaRepositories("io.bootify.usecase_app.repos")
@EnableTransactionManagement
public class DomainConfig {
}
