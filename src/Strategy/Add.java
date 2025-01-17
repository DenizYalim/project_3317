package Strategy;

import Model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.LocalDate;

public class Add implements Strategy {

    public void execute(String taskName, String deadline, String birthdayMessage, int index, ArrayList<Model> db) {
        System.out.println(taskName);
        System.out.println(deadline);
        if (taskName == null || taskName.isEmpty() || deadline == null || deadline.isEmpty()) {
            return;
        }

        try {
            LocalDate date = LocalDate.parse(deadline);
            Model m = new BasicMessage(taskName, date);
            if (birthdayMessage != null && birthdayMessage != " ") { // todo: check
                m = new BirthdayMessage(m, birthdayMessage);
            }
            db.add(m);
        }
        catch (Exception E) {
            // todo
        }
    }
}
