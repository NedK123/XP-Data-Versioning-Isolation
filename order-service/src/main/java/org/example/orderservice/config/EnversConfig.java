package org.example.orderservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EnversConfig {

    @Scope("prototype")
    @Bean
    public AuditReader auditReader(EntityManagerFactory factory) {
        return AuditReaderFactory.get(factory.createEntityManager());
    }

}
