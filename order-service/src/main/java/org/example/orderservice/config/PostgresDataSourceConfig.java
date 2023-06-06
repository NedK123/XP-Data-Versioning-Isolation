package org.example.orderservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "org.example.orderservice.persistence.validation",
        entityManagerFactoryRef = "postgresEntityManager",
        transactionManagerRef = "postgresTransactionManager",
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
public class PostgresDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().url("jdbc:postgresql://localhost:5433/mydatabase").build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(EntityManagerFactoryBuilder builder, @Qualifier("postgresDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("org.example.orderservice.persistence.validation")
                .persistenceUnit("postgresDataSource").build();
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
