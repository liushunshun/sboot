package com.xy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by XiuYang on 2016/10/26.
 */
@Configuration
public class MybatisConfig {

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
}
