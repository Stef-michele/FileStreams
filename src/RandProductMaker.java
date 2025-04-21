import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, recordCountField;
    private RandomAccessFile raf;
    private int recordCount = 0;

    public RandProductMaker() {
        super("Random Product Entry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(7, 2));

        nameField = new JTextField(35);
        descField = new JTextField(75);
        idField = new JTextField(6);
        costField = new JTextField(10);
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);

        JButton addButton = new JButton("Add Product");
        JButton quitButton = new JButton("Quit");

        add(new JLabel("Product Name:")); add(nameField);
        add(new JLabel("Description:")); add(descField);
        add(new JLabel("Product ID:")); add(idField);
        add(new JLabel("Cost:")); add(costField);
        add(new JLabel("Records Saved:")); add(recordCountField);
        add(addButton); add(quitButton);

        try {
            raf = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File could not be opened.");
            System.exit(1);
        }

        addButton.addActionListener(e -> addRecord());
        quitButton.addActionListener(e -> {
            try {
                raf.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        });

        setVisible(true);
    }

    private void addRecord() {
        try {
            String name = nameField.getText().trim();
            String desc = descField.getText().trim();
            String id = idField.getText().trim();
            double cost = Double.parseDouble(costField.getText().trim());

            if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            if (id.length() > 6 || name.length() > 35 || desc.length() > 75) {
                JOptionPane.showMessageDialog(this, "One or more fields exceed length limits.");
                return;
            }

            Product p = new Product(name, desc, id, cost);
            raf.seek(raf.length()); // move to end
            raf.writeBytes(p.getFixedLengthRecord());
            recordCount++;
            recordCountField.setText(String.valueOf(recordCount));

            // Clear fields
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for cost.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
