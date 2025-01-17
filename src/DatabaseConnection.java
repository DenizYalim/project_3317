import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Model.*;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;

    private String URL = "jdbc:mysql://localhost:3306";
    private String USERNAME = "root";
    private String PASSWORD = "";
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
        String databaseName = "todolist_db";
        String createDB = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        String useDB = "USE " + databaseName;
        String createBasicDaytables = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id INT PRIMARY KEY,
                    name VARCHAR(50),
                    deadline DATE 
                );
                
                """, BASICDAYSTABLE);
        String createBirthdayTable = String.format("""
                CREATE TABLE IF NOT EXISTS %s (
                    id INT PRIMARY KEY,
                    name VARCHAR(50),
                    deadline DATE,
                    birthdayMessage TEXT
                );
                """, BIRTHDAYSTABLE);
        try (
                Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(createDB);
            statement.execute(useDB);
            statement.execute(createBasicDaytables);
            statement.execute(createBirthdayTable);
            System.out.println("Database setup successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            // todo maybe create a JPANEL displaying the connection error ?
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void addItem(Model item) {

        String table;
        String columns;
        String values;
        if (item instanceof BirthdayMessage) { // Birthday Day
            table = BIRTHDAYSTABLE;
            columns = "(name, deadline, birthdayMessage)";
            values = "(" + item.getTaskName() + "," + item.getDeadline() + "," + ((BirthdayMessage) item).getBirthdayMessage() + ")";
        } else {    // Basic Day
            table = BASICDAYSTABLE;
            columns = "(name, deadline)";
            values = "(" + item.getTaskName() + "," + item.getDeadline() + ")";
        }

        String query = "INSERT INTO " + table + columns + " VALUES " + values + ";";
        try ( // todo: connection'ı genele alsak oradan çağırsak olur mu, tekrar initialize etmek yerine
              Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Item successfully added: " + item.getTaskName());
        } catch (SQLException E) {
            System.out.println("ERROR: ITEM WASN'T ADDED CORRECTLY");
        }

    }

    public void removeItem(Model item) {
        // todo
        String table;
        if (item instanceof BirthdayMessage) {
            table = BIRTHDAYSTABLE;
        } else {
            table = BASICDAYSTABLE;
        }
        String condition = "name is " + item.taskName; // maybe make this more discrete?
        String query = "DELETE FROM" + table + " WHERE " + condition;

        try ( // todo: connection'ı genele alsak oradan çağırsak olur mu, tekrar initialize etmek yerine
              Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
            System.out.println("Item removed from " + table + ": " + item.getTaskName());
        } catch (SQLException E) {
            System.out.println("ERROR: ITEM WASN'T REMOVED SUCCESSFULLY FROM TABLE: " + table);
        }
    }

    public void editItem(Model item, Model newItem) {
        // todo
        //Make sure that a change can be done such that all 3 columns are changed
    }

    public int getItemIndex(Model item) {
        String table;
        String conditions;
        if (item instanceof BirthdayMessage) {
            table = BIRTHDAYSTABLE;
            conditions = " = " + item.taskName + " = " + item.deadline + " = " + ((BirthdayMessage) item).getBirthdayMessage();
        } else {
            table = BASICDAYSTABLE;
            conditions = " = " + item.taskName + " = " + item.deadline;
        }

        String query = "SELECT " + table + " WHERE " + conditions; // todo
        return -1;
    }

    public void getTables() {
        // todo join both of the tables and return
    }
}
