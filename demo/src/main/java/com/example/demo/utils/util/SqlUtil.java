package com.example.demo.utils.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SqlUtil {
    private List<Statement> statements = new ArrayList<>();
    private List<ResultSet> resultSets = new ArrayList<>();


    private Connection connection = null;
    public static ConcurrentMap<Long, Connection> connectionConcurrentHashMap = new ConcurrentHashMap<>();

    public Connection getConnection() {
        return connection;
    }

    public void connect() throws Exception {
        String databaseURL = "jdbc:ucanaccess:///home/thai-luu/Downloads/test.accdb";
        connection = DriverManager.getConnection(databaseURL);
        connectionConcurrentHashMap.put(System.currentTimeMillis(), connection);
    }

    //Database disconnect//
    public void disconnect() {
        for (Statement statement : statements) {
            try {
                if (statement != null) statement.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (ResultSet resultSet : resultSets) {
            try {
                if (resultSet != null) resultSet.close();
            } catch (Exception e) {
                System.out.println("Unable to close resultSets");
            }
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connectionConcurrentHashMap.values().removeIf(connection1 -> connection1.equals(connection));
                connection.close();
            }

        } catch (Exception e) {
        }
    }


    //Execute sql query statement//
    public ResultSet exeQuery(String strSQL) throws SQLException {
        try {
            if (connection == null) {
                getConnection();
            }
            Statement statement = connection.createStatement();
            statements.add(statement);
            ResultSet resultSet = statement.executeQuery(strSQL);
            resultSets.add(resultSet);
            return resultSet;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean isClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

}
