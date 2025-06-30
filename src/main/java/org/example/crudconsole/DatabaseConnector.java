package org.example.crudconsole;

import java.sql.*;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/HW-001_CrudConsole";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}