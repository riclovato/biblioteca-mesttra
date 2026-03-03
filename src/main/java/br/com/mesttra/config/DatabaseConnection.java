package br.com.mesttra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    @Value("${app.datasource.url}")
    private String dbUrl;

    @Value("${app.datasource.username}")
    private String dbUsername;

    @Value("${app.datasource.password}")
    private String dbPassword;

    @Value("${app.datasource.driver-class-name}")
    private String driverClassName;

    public Connection obterConexao() throws SQLException {
        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver não encontrado: " + driverClassName, e);
        }
    }
}
