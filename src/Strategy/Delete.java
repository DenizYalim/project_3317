package Strategy;

import Model.Model;

import java.util.ArrayList;

public class Delete implements Strategy{
    public void execute(String taskName, String deadline, String birthdayMessage, int index, ArrayList<Model> db) {
        //System.out.println(index);
        // establish db connection
        if (index != -1) {
            db.remove(index);
        }
    }
}
