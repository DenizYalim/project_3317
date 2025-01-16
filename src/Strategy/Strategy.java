package Strategy;

import Model.Model;

import java.util.ArrayList;

public interface Strategy {
    void execute(String taskName, String deadline, String birthdayMessage, int index, ArrayList<Model> db);
}
