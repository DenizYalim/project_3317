package Model;

import java.util.Date;

public class BasicMessage extends Model{
    public BasicMessage(String taskname, Date deadline) {
        this.taskName = taskname;
        this.deadline = deadline;
    }
}
