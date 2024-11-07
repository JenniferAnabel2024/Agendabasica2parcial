package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MostrarContactos extends JFrame {

    private JTable contactosTable;
    private DefaultTableModel tableModel;
    private String userNick;  // Para almacenar el nick del usuario

    public MostrarContactos(String nick) {
        this.userNick = nick;  // Inicializa el nick del usuario
        setTitle("Lista de Contactos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de tabla
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
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
            PreparedStatement statement = connection.prepareStatement(
                "SELECT nombre, apellido, telefono, correo_electronico FROM contactos WHERE nick = ?");
            statement.setString(1, userNick);  // Filtrar por el nick del usuario

            ResultSet resultSet = statement.executeQuery();

            // Limpiar la tabla antes de llenarla
            tableModel.setRowCount(0);

            // Lleno la tabla con los datos de la base de datos
            boolean foundContacts = false;
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String email = resultSet.getString("correo_electronico");

                tableModel.addRow(new Object[]{nombre, apellido, telefono, email});
                foundContacts = true;  // Si encontró al menos un contacto, marcamos como encontrado
            }

            // Si no se encontraron contactos, mostramos un mensaje
            if (!foundContacts) {
                JOptionPane.showMessageDialog(this, "No se encontraron contactos para este usuario.");
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
            // Usamos un ejemplo de nick, en una implementación real este se pasa desde la clase anterior
            MostrarContactos mostrarContactos = new MostrarContactos("Pepino");  // El nick es un ejemplo
            mostrarContactos.setVisible(true);
        });
    }
}
