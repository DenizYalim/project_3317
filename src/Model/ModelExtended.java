package Model;

public abstract class ModelExtended extends Model{
    Model m;
    ModelExtended(Model m) {
        taskName = m.taskName;
        deadline = m.deadline;
        this.m = m;
    }
}
