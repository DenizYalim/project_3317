package Model;

import java.time.LocalDate;

public class BasicMessage extends Model{
    public BasicMessage(String taskname, LocalDate deadline) {
        this.taskName = taskname;
        this.deadline = deadline;
    }
}
