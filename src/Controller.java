import Model.*;
import Strategy.Strategy;
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

    public void addItem(String taskName, String deadline, String birthdayMessage) {

        printArraylist();
    }

    public void editItem(String taskName, String deadline, String birthdayMessage, int index){
        setStrategy(new Edit());
        strategy.execute(taskName, deadline, birthdayMessage, index);
    }

    public void deleteItem(String taskName, String deadline, String birthdayMessage, int index) {
        setStrategy(new Delete());
        strategy.execute(taskName, deadline, birthdayMessage, index);
    }



    void printArraylist() {
        System.out.println();
        Iterator<Model> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().taskName);
        }
    }


    public static void main(String[] args) {
        Controller c = new Controller();
    }
}
