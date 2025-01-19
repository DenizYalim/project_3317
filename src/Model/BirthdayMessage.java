package Model;

public class BirthdayMessage extends ModelExtended {
    String birthdayMessage;

    public BirthdayMessage(Model m, String birthdayMessage) {
        super(m);
        this.birthdayMessage = birthdayMessage;
    }

    public String getBirthdayMessage() {
        return birthdayMessage;
    }

    public void setBirthdayMessage(String birthdayMessage) {
        this.birthdayMessage = birthdayMessage;
    }

    @Override
    public String toString() {
        return "task: " + taskName + " - " + deadline + ", BIRTHDAY: " + birthdayMessage;
    }
}
