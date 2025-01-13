import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.*;

public class View extends JFrame {
    Controller controller;
    private JPanel panel1;
    private JTextField birthdayMessageField;
    private JTextField dayField;
    private JTextField dateField;
    private JList notificationList;
    private JList taskList;
    private JButton addButton;
    private JButton removeButton;
    private JButton editButton;

    View(Controller controller) {
        this.controller = controller;



        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the fields
                String day = dayField.getText();
                String date = dateField.getText();
                String birthdayMessage = birthdayMessageField.getText();
                // System.out.println("view: "+dayField.getText());

                // Pass the values to the controller's addItem method
                controller.addItem(day, date, birthdayMessage);
            }
        });
    }

    void createView() {
        this.pack();
        this.setVisible(true);
        this.setSize(400, 300);
        this.add(panel1);
        //System.exit(0);
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        // View view = new View(c);
        // view.createView();
    }
}
