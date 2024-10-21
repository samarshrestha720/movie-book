package com.movie_book.dbConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class TableCreateService {
    DbConnection dbc = new DbConnection();

    public void createSeatTable() throws SQLException {

        String createSeatTable = "CREATE TABLE `seat`(\r\n" + //
                "    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                "    `number` VARCHAR(255) NOT NULL,\r\n" + //
                "    `status` VARCHAR(255) NOT NULL\r\n" + //
                ");";
        Statement stmt = dbc.estConnection().createStatement();
        if (stmt.execute(createSeatTable)) {
            System.out.println("Table Seat table successfully!");
        } else {
            System.out.println("Seat table creation failed!");
        }

    }

    public int deleteTable(String tableName) throws SQLException {
        String deleteSql = "DROP TABLE " + tableName + ";";
        Statement stmt = dbc.estConnection().createStatement();

        int result = stmt.executeUpdate(deleteSql);
        if (result == 0) {
            System.out.println("Table " + tableName + " deleted successfully!");
        } else {
            System.out.println("Table " + tableName + " delete failed");
        }
        return result;
    }
}
