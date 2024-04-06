package javaproject.expense_management;

import java.sql.*;

public class Database {
    public static Connection connecDB() {
//public static void main(String[] args) {

//        try {
//            Connection connection = DriverManager.getConnection(
//                    "jdbc:mysql://127.0.0.1:3306/haidang_schema",
//                    "root",
//                    "Dang1906"
//            );
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
//            while (resultSet.next())
//            {
//                System.out.println(resultSet.getString("email"));
//                System.out.println(resultSet.getString("pass"));
//                System.out.println(resultSet.getString("answer"));
//                System.out.println(resultSet.getString("date"));
//
//
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/haidang_schema",
                    "root",
                    "Dang1906"
            );
        return connection;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
