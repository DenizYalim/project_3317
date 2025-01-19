import Model.*;
import Strategy.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {
    View view;
    DatabaseConnection databaseConnection;
    Strategy strategy;

    ArrayList<Model> arrayList;
    // ArrayList<Model> notificationDB = new ArrayList<>();

    Controller() throws SQLException {
        view = new View(this);
        databaseConnection = DatabaseConnection.createDatabaseConnection();
        arrayList = databaseConnection.getTables();
        view.createView();
        view.resetFields();
        view.setTaskList(arrayList);
        view.setNotificationList(getTodaysNotifications());
    }


    public void setStrategy(Strategy strat) {
        strategy = strat;
    }

    public void addItem() {
        setStrategy(new Add());
        executeStrategy();
    }

    public void editItem() {
        setStrategy(new Edit());
        executeStrategy();
    }

    public void deleteItem() {
        int index = 0;
        setStrategy(new Delete());
        executeStrategy();
    }

    void executeStrategy() {
        int index = view.getSelectedIndex();
        String taskName = view.getTaskName();
        String deadline = view.getDeadline();
        String birthdayMessage = view.getBirthdayMessage();
        strategy.execute(taskName, deadline, birthdayMessage, index, arrayList);

        view.resetFields();
        view.setTaskList(arrayList);
        view.setNotificationList(getTodaysNotifications());
        // printArraylist();

        databaseConnection.setTables(arrayList); // inserting the arraylist onto mysql
    }

    void printArraylist() {

        System.out.println();
        Iterator<Model> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        /*
        arrayList.forEach(System.out::println);
        */
    }

    ArrayList<Model> getTodaysNotifications() {
        ArrayList<Model> notifications = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        System.out.println(String.format("currentdate %s",currentDate));

        for(Model m: arrayList) {
            if (m.deadline.equals(currentDate)) {
                notifications.add(m);
            }
        }

        return notifications;
    }

    public static void main(String[] args) throws SQLException {
        Controller c = new Controller();
    }
}
