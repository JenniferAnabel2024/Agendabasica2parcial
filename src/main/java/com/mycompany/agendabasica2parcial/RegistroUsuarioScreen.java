package com.mycompany.agendabasica2parcial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RegistroUsuarioScreen {

    private Connection connection;
    private PreparedStatement statement;

    public void registrarUsuario(String nombre, String apellido, String correo, String nick, String telefono, String contrasena) {
        try {
            // Establece la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");

            // Prepara la consulta para insertar el nuevo usuario
            String query = "INSERT INTO contactos (nombre, apellido, email, nick, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            
            // Asigna los valores a los parámetros de la consulta
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correo);
            statement.setString(4, nick);
            statement.setString(5, telefono);
            statement.setString(6, contrasena);
            
            // Ejecuta la actualización
            statement.executeUpdate();

            // Mensaje de éxito
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito:\n" +
                    "Nombre: " + nombre + "\nNick: " + nick);

            // Abre CasillerosScreen en un hilo separado
            SwingUtilities.invokeLater(() -> {
                CasillerosScreen casilleros = new CasillerosScreen();
                casilleros.setVisible(true);
                // Si estás en un JFrame, puedes cerrar la ventana actual
                // dispose(); // Descomenta esto si deseas cerrar la ventana actual
            });

        } catch (Exception e) {
            // Mensaje de error
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage());
        } finally {
            // Cierra la conexión y el statement
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
