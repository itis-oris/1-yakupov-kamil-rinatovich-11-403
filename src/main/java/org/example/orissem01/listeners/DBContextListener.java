package org.example.orissem01.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.orissem01.utils.DBConnection;

@WebListener
public class DBContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        try {
            DBConnection.init();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.destroy();
    }
}
