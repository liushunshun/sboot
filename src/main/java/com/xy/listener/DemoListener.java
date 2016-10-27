package com.xy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by XiuYang on 2016/10/27.
 */
public class DemoListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("==>DemoListener启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
