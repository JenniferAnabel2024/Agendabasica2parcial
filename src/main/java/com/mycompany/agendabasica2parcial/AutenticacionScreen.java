package com.mycompany.agendabasica2parcial; // Asegúrate de que esta línea esté al inicio

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                irARegistro(); // Método que lleva a la ventana de registro
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistrar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void iniciarSesion() {
        // Lógica para validar el inicio de sesión (asegúrate de manejar excepciones aquí)
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        // Aquí deberías validar el usuario contra la base de datos
        // Si hay un error, mostrar un mensaje sin cerrar la ventana
        try {
            // Simulación de validación (reemplaza esto con tu lógica)
            if (nick.equals("admin") && contrasena.equals("admin")) {
                // Si las credenciales son correctas, muestra la lista de contactos o la siguiente ventana
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                new CasillerosScreen().setVisible(true); // Aquí abrirías tu ventana de contactos
                dispose(); // Cierra la ventana de autenticación
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void irARegistro() {
        new RegistroUsuarioScreen().setVisible(true); // Muestra la ventana de registro
        dispose(); // Cierra la ventana de autenticación
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutenticacionScreen().setVisible(true));
    }
}
