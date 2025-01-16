package Model;

import java.util.Date;

public abstract class Model {
    public String taskName;
    public Date deadline;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return taskName + ", date: " + deadline;
    }
}
