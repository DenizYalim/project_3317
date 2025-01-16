import Model.*;
import Strategy.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Controller {
    View view;
    DatabaseConnection databaseConnection;
    Strategy strategy;

    ArrayList<Model> arrayList = new ArrayList<>(); // todo: remove later

    Controller() {
        view = new View(this);
        // databaseConnection = DatabaseConnection.createDatabaseConnection(); //todo uncomment to establish the db

        view.createView();
    }

    public void addItemToDB(Model item) {
        // databaseConnection.addItem(item);
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
        view.setTaskList(arrayList);
        printArraylist();
    }


    void printArraylist() {
        /*
        System.out.println();
        Iterator<Model> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        */
        arrayList.forEach(System.out::println);
    }


    public static void main(String[] args) {
        Controller c = new Controller();
    }
}
