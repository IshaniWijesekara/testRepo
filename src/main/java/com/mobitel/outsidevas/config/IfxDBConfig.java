package com.mobitel.outsidevas.config;


import com.mobitel.outsidevas.util.exception.DataFetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@ComponentScan(basePackages = "com.mobitel.outsidevas.entity.ifx")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ifxEntityManager",
        transactionManagerRef = "ifxTransactionManager",
        basePackages = "com.mobitel.outsidevas"
)
@PropertySource("classpath:application.properties")
public class IfxDBConfig {

    Environment environment;

    public IfxDBConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Only one primary Datasource and EntityManager and Transaction Manager
     *
     * @return
     */

    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.ifx.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.ifx.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.ifx.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.ifx.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.ifx.datasource.password"));


        //JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        //DataSource dataSource = dataSourceLookup.getDataSource(environment.getProperty("spring.ifx.datasource.jndi"));
        return dataSource;
    }


    @Bean(name = "ifxEntityManager")
    public LocalContainerEntityManagerFactoryBean ifxEntityManagerFactoryBean(@Qualifier("datasource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("ifxPU");
        emf.setJpaProperties(hibernateProperties());
        emf.setPackagesToScan("com.mobitel.outsidevas.entity.ifx");
        emf.setJpaVendorAdapter(getHibernateAdapter());
        return emf;
    }

    @Bean(name = "ifxTransactionManager")
    public PlatformTransactionManager ifxTransactionManager(@Qualifier("ifxEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.InformixDialect");
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
    @Bean("ifxJdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource());
        return jdbcTemplate;
    }
}
