package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticacionScreen extends JFrame {

    private JTextField txtNick;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion, btnRegistrar;

    public AutenticacionScreen() {
        setTitle("Autenticación");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Nick:"));
        txtNick = new JTextField();
        panel.add(txtNick);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        panel.add(btnIniciarSesion);

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        panel.add(btnRegistrar);

        add(panel);
    }

    private void iniciarSesion() {
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Establece la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");

            // Prepara la consulta para verificar las credenciales del usuario en la tabla 'autenticacion'
            String query = "SELECT * FROM autenticacion WHERE nick = ? AND contrasena = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, nick);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Si el usuario existe, intenta obtener el nick y abre la pantalla de casilleros
                String userNick = resultSet.getString("nick"); // Manejo de SQLException
                SwingUtilities.invokeLater(() -> {
                    CasillerosScreen casilleros = new CasillerosScreen(userNick); // Usar userNick
                    casilleros.setVisible(true);
                    dispose(); // Cierra la ventana de autenticación
                });
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Por favor regístrese.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        } finally {
            // Cierra la conexión y el statement
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void registrarUsuario() {
        SwingUtilities.invokeLater(() -> {
            RegistroUsuarioScreen registroScreen = new RegistroUsuarioScreen();
            registroScreen.setVisible(true);
            dispose(); // Cierra la ventana de autenticación
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutenticacionScreen authScreen = new AutenticacionScreen();
            authScreen.setVisible(true);
        });
    }
}
