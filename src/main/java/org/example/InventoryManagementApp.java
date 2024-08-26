package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class InventoryManagementApp extends JFrame {
    private Inventory inventory;
    private JPanel mainPanel;
    private JTable phoneTable;
    private DefaultTableModel tableModel;
    private JButton editButton;
    private JButton deleteButton;

    public InventoryManagementApp() {
        // Initialize the inventory object
        inventory = new Inventory();

        // Set up the main window properties
        setTitle("Phone Shop Inventory Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        // Create the main menu panel
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton addPhoneButton = new JButton("Add Phone");
        JButton viewPhonesButton = new JButton("View Phones");

        menuPanel.add(addPhoneButton);
        menuPanel.add(viewPhonesButton);

        // Add action listeners for buttons
        addPhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show add phone form
                showAddPhoneForm();
            }
        });

        viewPhonesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show phone inventory in table format
                showPhoneList();
            }
        });

        // Add panels to main panel
        mainPanel.add(menuPanel, "Menu");

        // Set the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private void showAddPhoneForm() {
        // Create the add phone form panel
        JPanel addPhonePanel = new JPanel(new GridLayout(6, 2, 10, 10));
        addPhonePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create labels and text fields for each phone attribute
        JLabel modelNameLabel = new JLabel("Model Name:");
        JTextField modelNameField = new JTextField();

        JLabel brandLabel = new JLabel("Brand:");
        JTextField brandField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();

        JLabel specificationsLabel = new JLabel("Specifications:");
        JTextField specificationsField = new JTextField();

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        addPhonePanel.add(modelNameLabel);
        addPhonePanel.add(modelNameField);
        addPhonePanel.add(brandLabel);
        addPhonePanel.add(brandField);
        addPhonePanel.add(priceLabel);
        addPhonePanel.add(priceField);
        addPhonePanel.add(quantityLabel);
        addPhonePanel.add(quantityField);
        addPhonePanel.add(specificationsLabel);
        addPhonePanel.add(specificationsField);
        addPhonePanel.add(saveButton);
        addPhonePanel.add(cancelButton);

        // Add action listeners for save and cancel buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String modelName = modelNameField.getText();
                    String brand = brandField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    String specifications = specificationsField.getText();

                    Phone phone = new Phone(modelName, brand, price, quantity, specifications);
                    inventory.addPhone(phone);
                    JOptionPane.showMessageDialog(null, "Phone added successfully!");

                    // Clear fields after saving
                    modelNameField.setText("");
                    brandField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    specificationsField.setText("");

                    // Return to main menu
                    CardLayout cl = (CardLayout) (mainPanel.getLayout());
                    cl.show(mainPanel, "Menu");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid inputs!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to main menu
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Menu");
            }
        });

        // Add the add phone panel to the main panel and show it
        mainPanel.add(addPhonePanel, "AddPhone");
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "AddPhone");
    }

    private void showPhoneList() {
        // Create a panel to display the phone list in a table format
        JPanel listPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Model Name", "Brand", "Price", "Quantity", "Specifications"};

        // Fetch data from inventory and populate the table
        tableModel = new DefaultTableModel(columnNames, 0);
        for (Phone phone : inventory.getAllPhones()) {
            Object[] rowData = {
                    phone.getModelName(),
                    phone.getBrand(),
                    phone.getPrice(),
                    phone.getQuantity(),
                    phone.getSpecifications()
            };
            tableModel.addRow(rowData);
        }

        phoneTable = new JTable(tableModel);
        phoneTable.setFillsViewportHeight(true);
        phoneTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(phoneTable);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Initialize edit and delete buttons
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Add action listeners for edit and delete buttons
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = phoneTable.getSelectedRow();
                if (selectedRow >= 0) {
                    editPhone(selectedRow);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = phoneTable.getSelectedRow();
                if (selectedRow >= 0) {
                    deletePhone(selectedRow);
                }
            }
        });

        // Add selection listener to the table
        phoneTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && phoneTable.getSelectedRow() >= 0) {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add a button to go back to the menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to main menu
                CardLayout cl = (CardLayout) (mainPanel.getLayout());
                cl.show(mainPanel, "Menu");
            }
        });
        buttonPanel.add(backButton);

        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the list panel to the main panel and show it
        mainPanel.add(listPanel, "ViewPhones");
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "ViewPhones");
    }

    private void editPhone(int row) {
        String modelName = (String) tableModel.getValueAt(row, 0);
        Phone phoneToEdit = inventory.searchPhone(modelName);

        if (phoneToEdit != null) {
            JTextField modelNameField = new JTextField(phoneToEdit.getModelName());
            JTextField brandField = new JTextField(phoneToEdit.getBrand());
            JTextField priceField = new JTextField(String.valueOf(phoneToEdit.getPrice()));
            JTextField quantityField = new JTextField(String.valueOf(phoneToEdit.getQuantity()));
            JTextField specificationsField = new JTextField(phoneToEdit.getSpecifications());

            Object[] message = {
                    "Model Name:", modelNameField,
                    "Brand:", brandField,
                    "Price:", priceField,
                    "Quantity:", quantityField,
                    "Specifications:", specificationsField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edit Phone", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String newModelName = modelNameField.getText();
                    String newBrand = brandField.getText();
                    double newPrice = Double.parseDouble(priceField.getText());
                    int newQuantity = Integer.parseInt(quantityField.getText());
                    String newSpecifications = specificationsField.getText();

                    Phone updatedPhone = new Phone(newModelName, newBrand, newPrice, newQuantity, newSpecifications);
                    inventory.updatePhone(modelName, updatedPhone);
                    refreshTable();
                    JOptionPane.showMessageDialog(null, "Phone updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid inputs!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deletePhone(int row) {
        String modelName = (String) tableModel.getValueAt(row, 0);
        inventory.deletePhone(modelName);
        refreshTable();
        JOptionPane.showMessageDialog(null, "Phone deleted successfully!");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Phone phone : inventory.getAllPhones()) {
            Object[] rowData = {
                    phone.getModelName(),
                    phone.getBrand(),
                    phone.getPrice(),
                    phone.getQuantity(),
                    phone.getSpecifications()
            };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        // Main method to launch the application
        SwingUtilities.invokeLater(() -> {
            InventoryManagementApp app = new InventoryManagementApp();
            app.setVisible(true);
        });
    }
}
