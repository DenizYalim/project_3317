package Model;

import java.time.LocalDate;

public abstract class Model {
    public String taskName;
    public LocalDate deadline;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "taskName: "+ taskName + ", date: " + deadline;
    }
}
