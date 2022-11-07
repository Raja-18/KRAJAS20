package com.wallet.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlUtility {

    public static Connection getConnectionToMySQL() {
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/wallet", "root", "xbox18");
            //System.out.println("Connection to MYSQL successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

