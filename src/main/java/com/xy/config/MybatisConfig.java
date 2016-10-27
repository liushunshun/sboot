package com.xy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by XiuYang on 2016/10/26.
 */
@Configuration
public class MybatisConfig {

    private static Logger log = LoggerFactory.getLogger(MybatisConfig.class);

    @Autowired
    private JdbcConnectionSettings jdbcConnectionSettings;

    @Bean
    public DataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(jdbcConnectionSettings.getDriver());
        ds.setUsername(jdbcConnectionSettings.getUsername());
        ds.setPassword(jdbcConnectionSettings.getPassword());
        ds.setUrl(jdbcConnectionSettings.getUrl());
        ds.setMaxActive(jdbcConnectionSettings.getMaxActive());
        ds.setValidationQuery(jdbcConnectionSettings.getValidationQuery());
        ds.setTestOnBorrow(jdbcConnectionSettings.getTestOnBorrow());
        ds.setTestOnReturn(jdbcConnectionSettings.getTestOnReturn());
        ds.setTestWhileIdle(jdbcConnectionSettings.getTestWhileIdle());
        ds.setTimeBetweenEvictionRunsMillis(jdbcConnectionSettings.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(jdbcConnectionSettings.getMinEvictableIdleTimeMillis());
        return ds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        sqlSessionFactory.setFailFast(true);
        sqlSessionFactory.setMapperLocations(getResource("mapper", "**/*.xml"));
        return sqlSessionFactory.getObject();
    }

    public Resource[] getResource(String basePackage, String pattern ) throws IOException {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment()
                .resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        return resources;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        log.debug("> transactionManager");
        return new DataSourceTransactionManager(dataSource());
    }


    @PostConstruct
    public void postConstruct() {
        log.info("jdbc.settings={}", jdbcConnectionSettings);
    }
}
