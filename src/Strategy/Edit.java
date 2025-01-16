package Strategy;

import Model.Model;

import java.util.ArrayList;

public class Edit implements Strategy{
    public void execute(String taskName, String deadline, String birthdayMessage, int index, ArrayList<Model> db) {
        //Split arrayList into two, excluding the index.

        // Add a new Model with the desired elements.

        // Combine the Lists into one and change the db into it.

        if (index == -1) {
            return;
        }

        ArrayList<Model> db1 = new ArrayList<> (db.subList(0,index));

        ArrayList<Model> db2 = new ArrayList<>(db.subList(index+1, db.size()));

        Add add = new Add();
        add.execute(taskName, deadline, birthdayMessage, index, db1);

        db1.addAll(db2);

        // db = db1; // doesn't work, original isn't affected



        db.clear();
        db.addAll(db1);
    }
}
