package Strategy;

import Model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Add implements Strategy{

    public void execute(String taskName, String deadline, String birthdayMessage, int index, ArrayList<Model> db) {
        // todo convert String deadline to Date deadline

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Model m = new BasicMessage(taskName, date);
        if (birthdayMessage != null && birthdayMessage != " ") { // todo: check
            m = new BirthdayMessage(m, birthdayMessage);
        }
        db.add(m); // if arraylist
        // addItemToDB(m);
        System.out.println("Item added, " + db.get(db.size()-1));

    }
}
