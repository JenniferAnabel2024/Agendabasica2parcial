package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AutenticacionScreen extends JFrame {

    private JTextField txtNick;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion, btnRegistrar;

    public AutenticacionScreen() {
        setTitle("Iniciar Sesión");
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
                iniciarSesion(); // Método que valida el inicio de sesión
            }
        });

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario(); // Llama al método para registrar usuario
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistrar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void iniciarSesion() {
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&]).{12,}$";

        if (!contrasena.matches(regex)) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 12 caracteres, una mayúscula, una minúscula y un carácter especial.");
            return;
        }

        if (verificarCredenciales(nick, contrasena)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
            new CasillerosScreen().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usted debe registrarse.");
        }
    }

    private boolean verificarCredenciales(String nick, String contrasena) {
        boolean existeUsuario = false;

        String url = "jdbc:mysql://localhost:3306/mibasededatosagenda";
        String usuarioDB = "root"; 
        String contrasenaDB = ""; 

        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contrasenaDB)) {
            // Cambiamos a la tabla 'autenticacion'
            String sql = "SELECT * FROM autenticacion WHERE nick = ? AND contrasena = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, nick);
            statement.setString(2, contrasena);

            ResultSet resultado = statement.executeQuery();
            existeUsuario = resultado.next();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage());
        }

        return existeUsuario;
    }

    private void registrarUsuario() {
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&]).{12,}$";

        if (!contrasena.matches(regex)) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 12 caracteres, una mayúscula, una minúscula y un carácter especial.");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/mibasededatosagenda";
        String usuarioDB = "root"; // Cambia esto por tu usuario de MySQL
        String contrasenaDB = ""; // Cambia esto por tu contraseña de MySQL

        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contrasenaDB)) {
            // Cambiamos a la tabla 'autenticacion'
            String sql = "INSERT INTO autenticacion (nick, contrasena) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, nick);
            statement.setString(2, contrasena);

            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutenticacionScreen().setVisible(true));
    }
}
