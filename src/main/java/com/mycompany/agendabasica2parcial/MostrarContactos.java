package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MostrarContactos extends JFrame {
    
    private JTable contactosTable;
    private DefaultTableModel tableModel;

    public MostrarContactos() {
        setTitle("Lista de Contactos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Teléfono");
        tableModel.addColumn("Email");

        // Creo la tabla
        contactosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(contactosTable);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar contactos desde la base de datos
        cargarContactos();
    }

    private void cargarContactos() {
        String url = "jdbc:mysql://127.0.0.1:3306/mibasededatosagenda";
        String user = "root";
        String password = ""; // Cambia esto a tu contraseña si es necesario
        
        Connection connection = null;
        try {
            // Conectar a la base de datos
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre, telefono, email FROM contactos");

            // Lleno la tabla con los datos de la base de datos
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String telefono = resultSet.getString("telefono");
                String email = resultSet.getString("email");
                tableModel.addRow(new Object[]{nombre, telefono, email});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar contactos: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MostrarContactos mostrarContactos = new MostrarContactos();
            mostrarContactos.setVisible(true);
        });
    }
}
