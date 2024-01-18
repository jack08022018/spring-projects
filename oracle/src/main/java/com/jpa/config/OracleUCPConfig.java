//package com.jpa.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import oracle.ucp.jdbc.PoolDataSourceFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Objects;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//@EnableJpaRepositories(basePackages = "com.jpa.repository")
//public class OracleUCPConfig {
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Bean(name = "dataSource")
//    public DataSource dataSource() throws SQLException {
//        var pool = PoolDataSourceFactory.getPoolDataSource();
//        pool.setURL(url);
//        pool.setUser(username);
//        pool.setPassword(password);
//        pool.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
//        pool.setInitialPoolSize(5);
//        pool.setMinPoolSize(5);
//        pool.setMaxPoolSize(10);
//        pool.setQueryTimeout(30000);
//        return pool;
//    }
//
//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//           final @Qualifier("dataSource") DataSource dataSource) {
//        var pros = new HashMap<String, String>();
//        pros.put("spring.jpa.hibernate.ddl-auto", "none");
//        pros.put("hibernate.hql.bulk_id_strategy.global_temporary.create_tables", "false");
//        return builder
//                .dataSource(dataSource)
//                .packages("com.jpa.entity")
//                .properties(pros)
//                .build();
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(
//            final @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
//    ) {
//       return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
//    }
//
//}