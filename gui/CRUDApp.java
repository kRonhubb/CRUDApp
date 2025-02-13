import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CRUDApp extends JFrame {
    private JTextField nameField, cityField, ageField;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton updateButton, deleteButton;

    public CRUDApp() {
        setTitle("User Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));
        
        Font font = new Font("Tahoma", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Details"));
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
 
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(font);
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(font);

        nameField = new JTextField(10);
        nameField.setFont(font);
        cityField = new JTextField(10);
        cityField.setFont(font);
        ageField = new JTextField(5);
        ageField.setFont(font);

        JButton addButton = new JButton("➕ Add");
        styleButton(addButton, new Color(0, 153, 255));

        updateButton = new JButton("✏ Update");
        styleButton(updateButton, new Color(255, 153, 0));
        updateButton.setEnabled(false);

        deleteButton = new JButton("🗑 Delete");
        styleButton(deleteButton, new Color(255, 51, 51));
     
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(nameLabel, gbc);
        gbc.gridx = 1; inputPanel.add(nameField, gbc);
        gbc.gridx = 2; inputPanel.add(cityLabel, gbc);
        gbc.gridx = 3; inputPanel.add(cityField, gbc);
        gbc.gridx = 4; inputPanel.add(ageLabel, gbc);
        gbc.gridx = 5; inputPanel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; inputPanel.add(addButton, gbc);
        gbc.gridx = 2; inputPanel.add(updateButton, gbc);
        gbc.gridx = 4; inputPanel.add(deleteButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Name", "City", "Age"}, 0);
        table = new JTable(tableModel);
        table.setFont(font);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 153, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(204, 229, 255));
        add(new JScrollPane(table), BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            if (validateFields()) {
                tableModel.addRow(new Object[]{nameField.getText(), cityField.getText(), ageField.getText()});
                clearFields();
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 && validateFields()) {
                tableModel.setValueAt(nameField.getText(), selectedRow, 0);
                tableModel.setValueAt(cityField.getText(), selectedRow, 1);
                tableModel.setValueAt(ageField.getText(), selectedRow, 2);
                clearFields();
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(selectedRow);
                    clearFields();
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                nameField.setText(table.getValueAt(selectedRow, 0).toString());
                cityField.setText(table.getValueAt(selectedRow, 1).toString());
                ageField.setText(table.getValueAt(selectedRow, 2).toString());
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() || cityField.getText().trim().isEmpty() || ageField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void clearFields() {
        nameField.setText("");
        cityField.setText("");
        ageField.setText("");
        table.clearSelection();
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CRUDApp().setVisible(true));
    }
}
