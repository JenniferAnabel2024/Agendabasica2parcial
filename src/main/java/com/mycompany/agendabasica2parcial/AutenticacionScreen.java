package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticacionScreen extends JFrame {
    private JTextField txtNick;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion, btnRegistrar;

    // Mensajes de validación
    private JLabel lblNickMessage;
    private JLabel lblContrasenaMessage;

    public AutenticacionScreen() {
        setTitle("Autenticación");
        setSize(900, 500); // Tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel y diseño
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Componentes
        JLabel lblNick = new JLabel("Nick:");
        txtNick = new JTextField();
        txtNick.setPreferredSize(new Dimension(150, 30)); // Ajustamos el tamaño del campo de texto

        lblNickMessage = new JLabel("El nick debe contener solo letras");
        lblNickMessage.setForeground(Color.RED);

        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();
        txtContrasena.setPreferredSize(new Dimension(150, 30)); // Ajustamos el tamaño del campo de texto

        lblContrasenaMessage = new JLabel("La contraseña debe contener al menos 12 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial");
        lblContrasenaMessage.setForeground(Color.RED);

        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.addActionListener(e -> validarCampos());
        btnIniciarSesion.setPreferredSize(new Dimension(200, 40));

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnRegistrar.setPreferredSize(new Dimension(150, 40));

        // Diseño del GroupLayout
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblNick)
                    .addComponent(txtNick)
                    .addComponent(lblNickMessage)
                    .addComponent(lblContrasena)
                    .addComponent(txtContrasena)
                    .addComponent(lblContrasenaMessage)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIniciarSesion)
                        .addGap(50)
                        .addComponent(btnRegistrar)
                    )
                )
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblNick)
                .addComponent(txtNick)
                .addComponent(lblNickMessage)
                .addComponent(lblContrasena)
                .addComponent(txtContrasena)
                .addComponent(lblContrasenaMessage)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarSesion)
                    .addComponent(btnRegistrar))
        );

        // Agregamos el panel al marco
        add(panel);
    }

    private void validarCampos() {
        boolean esValido = true;

        // Validación de 'nick'
        if (!txtNick.getText().matches("[a-zA-Z]+")) {
            lblNickMessage.setText("El nick debe contener solo letras.");
            JOptionPane.showMessageDialog(this, "El nick debe contener solo letras.", "Error", JOptionPane.ERROR_MESSAGE);
            esValido = false;
        } else {
            lblNickMessage.setText("El nick debe contener solo letras"); // Guía persistente
        }

        // Validación de 'contraseña'
        String contrasena = new String(txtContrasena.getPassword());
        if (!contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{12,}$")) {
            lblContrasenaMessage.setText("La contraseña no cumple con los requisitos.");
            JOptionPane.showMessageDialog(this, "La contraseña debe contener al menos 12 caracteres, incluyendo una letra mayúscula, una minúscula, un número y un carácter especial.", "Error", JOptionPane.ERROR_MESSAGE);
            esValido = false;
        } else {
            lblContrasenaMessage.setText("La contraseña debe contener al menos 12 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial"); // Guía persistente
        }

        if (esValido) {
            iniciarSesion();
        }
    }

    private void iniciarSesion() {
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");
            String query = "SELECT * FROM autenticacion WHERE nick = ? AND contrasena = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, nick);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userNick = resultSet.getString("nick");
                SwingUtilities.invokeLater(() -> {
                    CasillerosScreen casilleros = new CasillerosScreen(userNick);
                    casilleros.setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Por favor regístrese.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
        } finally {
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
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutenticacionScreen authScreen = new AutenticacionScreen();
            authScreen.setVisible(true);
        });
    }
}
