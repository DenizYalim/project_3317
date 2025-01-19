import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;

import Model.*;
import com.mysql.cj.protocol.Resultset;

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
        if (debug) {
            System.out.println(q);
        }
        executeStatement(q);
    }

    public ResultSet executeStatement(String q) {
        String databaseName = "todolist_db";
        String createDB = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDB = "USE " + databaseName;

        ResultSet resultSet = null;
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(createDB);
            statement.execute(useDB);


            if (statement.execute(q)) {
                resultSet = statement.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // todo: maybe create a JPANEL displaying the error ? // I probably wont bother
        }
        return resultSet;
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

    public ArrayList<Model> getTables() throws SQLException {
        // todo join both of the tables and return // this wont work tho

        ArrayList<Model> arrayList = new ArrayList<>();

        String basicDayTableStatement = String.format("SELECT * FROM %s", BASICDAYSTABLE);

        String birthDayTableStatement = String.format("SELECT * FROM %s", BIRTHDAYSTABLE);


        String databaseName = "todolist_db";
        String createDB = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDB = "USE " + databaseName;

        ResultSet basicDaySet = null;
        ResultSet birthDaySet = null;

        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute(createDB);
            statement.execute(useDB);


            if (statement.execute(basicDayTableStatement)) {
                basicDaySet = statement.getResultSet();
            }

            while (basicDaySet.next()) {
                String name = basicDaySet.getString("name");
                LocalDate deadline = basicDaySet.getDate("deadline").toLocalDate();

                arrayList.add(new BasicMessage(name, deadline));
            }

            if (statement.execute(birthDayTableStatement)) {
                birthDaySet = statement.getResultSet();
            }


            while (birthDaySet.next()) {
                String name = birthDaySet.getString("name");
                LocalDate deadline = birthDaySet.getDate("deadline").toLocalDate();
                String birthdayMsg = birthDaySet.getString("birthdayMessage");

                BasicMessage bsc = new BasicMessage(name, deadline);
                arrayList.add(new BirthdayMessage(bsc, birthdayMsg));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            // todo: maybe create a JPANEL displaying the error ? // I probably wont bother
        }

        return arrayList;
    }
}
