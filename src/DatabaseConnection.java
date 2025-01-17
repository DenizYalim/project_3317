import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

import Model.*;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;

    private String URL = passwordConfigForDB.getURL();
    private String USERNAME = passwordConfigForDB.getUsername();
    private String PASSWORD = passwordConfigForDB.getPassword();
    private String BASICDAYSTABLE = "basicdayDays";
    private String BIRTHDAYSTABLE = "birthdayDays";

    private DatabaseConnection() {
        initializeDatabase();
    }

    public static DatabaseConnection createDatabaseConnection() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void initializeDatabase() {

        String createBasicDaytables = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50),
                    deadline DATE 
                );
                                
                """, BASICDAYSTABLE);
        String createBirthdayTable = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50),
                    deadline DATE,
                    birthdayMessage TEXT
                );
                """, BIRTHDAYSTABLE);

        executeStatement(createBasicDaytables);
        executeStatement(createBirthdayTable);
        System.out.println("Database setup successfully");
    }

    public void executeStatement(String q, boolean debug) {
        if(debug) {
            System.out.println(q);
        }
        executeStatement(q);
    }
    public void executeStatement(String q) {
        String databaseName = "todolist_db";
        String createDB = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDB = "USE " + databaseName;
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(createDB);
            statement.execute(useDB);
            ;
            statement.execute(q);
        } catch (SQLException e) {
            e.printStackTrace();
            // todo maybe create a JPANEL displaying the error ?
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void emptyTable(String table) {
        if (!table.equals(BASICDAYSTABLE) && !table.equals(BIRTHDAYSTABLE)) {
            return;
        }

        String statement = "DELETE FROM " + table;
        /*
        if (table.equals(BIRTHDAYSTABLE)) {
            statement += BIRTHDAYSTABLE;
        }
        else {
            statement = String.format("DELETE FROM %s", BASICDAYSTABLE);
        }
        */

        executeStatement(statement, false);
        System.out.println("Deleted table: " + table);
    }

    public void setTables(ArrayList<Model> arrayList) {
        emptyTable(BASICDAYSTABLE);
        emptyTable(BIRTHDAYSTABLE);

        for (Model m : arrayList) {
            String statement = "INSERT INTO ";

            String name = m.getTaskName();
            LocalDate deadline = m.getDeadline();

            if (m instanceof BirthdayMessage) {
                String bdmessage = ((BirthdayMessage) m).getBirthdayMessage();
                statement += String.format("%s (name, deadline, birthdayMessage) VALUES ('%s', '%s', '%s')", BIRTHDAYSTABLE, name, deadline, bdmessage);
            } else {
                statement += String.format("%s (name, deadline) VALUES ('%s', '%s')", BASICDAYSTABLE, name, deadline);
            }
            executeStatement(statement, true);
        }
        System.out.println("Inserted arraylist onto MySql");
    }

    public ArrayList<Model> getTables() {
        // todo join both of the tables and return
        return null;
    }
}
