package com.mobitel.outsidevas.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "oracleEntityManager",
        transactionManagerRef = "oracleTransactionManager",
        basePackages = "com.mobitel.outsidevas"
)
@PropertySource("classpath:application.properties")
public class OracleDBConfig {

    Environment environment;

    public OracleDBConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * These can't be  Primary
     *
     * @return
     */

    @Primary
    @Bean(name = "oracleDatasource")
    @ConfigurationProperties(prefix = "spring.oracle.datasource")
    public DataSource oracleDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.oracle.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.oracle.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.oracle.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.oracle.datasource.password"));
        return dataSource;
    }


    @Primary
    @Bean(name = "oracleEntityManager")
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactoryBean(@Qualifier("oracleDatasource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("oraclePU");
        emf.setJpaProperties(hibernateProperties());
        emf.setPackagesToScan("com.mobitel.outsidevas");
        emf.setJpaVendorAdapter(getHibernateAdapter());
        return emf;
    }

    @Primary
    @Bean(name = "oracleTransactionManager")
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        props.setProperty("hibernate.show_sql", "false");
        return props;
    }

    @Bean
    public JpaVendorAdapter getHibernateAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    /**
     * Configure the jdbcTemplate according to the data source
     *
     * @return
     */
    @Primary
    @Bean("oracleJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.oracleDataSource());
        return jdbcTemplate;
    }
}
