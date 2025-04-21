import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton searchButton, quitButton;

    private static final int RECORD_SIZE = 126;

    public RandProductSearch() {
        super("Product Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchButton = new JButton("Search");

        topPanel.add(new JLabel("Enter partial product name:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        quitButton = new JButton("Quit");

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> performSearch());
        quitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        resultArea.setText("");

        try (RandomAccessFile raf = new RandomAccessFile("products.dat", "r")) {
            long numRecords = raf.length() / RECORD_SIZE;

            for (int i = 0; i < numRecords; i++) {
                raf.seek(i * RECORD_SIZE);

                byte[] idBytes = new byte[6];
                byte[] nameBytes = new byte[35];
                byte[] descBytes = new byte[75];
                byte[] costBytes = new byte[10];

                raf.read(idBytes);
                raf.read(nameBytes);
                raf.read(descBytes);
                raf.read(costBytes);

                String id = new String(idBytes).trim();
                String name = new String(nameBytes).trim();
                String desc = new String(descBytes).trim();
                String cost = new String(costBytes).trim();

                if (name.toLowerCase().contains(searchTerm)) {
                    resultArea.append("ID: " + id + "\n");
                    resultArea.append("Name: " + name + "\n");
                    resultArea.append("Description: " + desc + "\n");
                    resultArea.append("Cost: $" + cost + "\n\n");
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
