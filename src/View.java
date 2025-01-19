import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                controller.addItem();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deleteItem();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.editItem();
            }
        });
    }

    void setTaskList(ArrayList<Model> models) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Model model : models) {
            listModel.addElement(model.toString());
        }

        taskList.setModel(listModel);
    }

    int getSelectedIndex() {
        return taskList.getSelectedIndex();
    }

    void createView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(400, 300);
        this.add(panel1);
        //System.exit(0);
    }

    String getTaskName() {
        return dayField.getText();
    }

    String getDeadline() {
        return dateField.getText();
    }

    String getBirthdayMessage() {
        return birthdayMessageField.getText();
    }

    void setNotificationList(ArrayList<Model> db) {
        DefaultListModel listModel = new DefaultListModel();
        for(Model m : db) {
            listModel.addElement(m);
        }
        notificationList.setModel(listModel);
    }

    public void resetFields() {
        dayField.setText("");
        dateField.setText("yyyy-MM-dd");
        birthdayMessageField.setText("");
    }

    public static void main(String[] args) throws SQLException {
        Controller c = new Controller();
        // View view = new View(c);
        // view.createView();
    }
}
