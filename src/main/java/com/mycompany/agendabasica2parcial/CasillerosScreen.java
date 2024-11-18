package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CasillerosScreen extends JFrame {

    private JTextField txtNombre, txtApellido, txtCorreo, txtTelefono;
    private JButton btnAgregar, btnModificar, btnMostrar, btnVolver;
    private JLabel lblErrorNombre, lblErrorApellido, lblErrorCorreo, lblErrorTelefono;
    private String userNick;  // Para almacenar el nick del usuario

    public CasillerosScreen(String userNick) {
        this.userNick = userNick; // Inicializa el nick del usuario
        setTitle("Bienvenido " + userNick + " ! Empieza a administrar tus contactos! <3");
        setSize(400, 400); // Aumenté el tamaño para que se vea cómodo
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        lblErrorNombre = new JLabel("Solo se aceptan letras");
        lblErrorNombre.setForeground(Color.RED);
        lblErrorNombre.setVisible(false);  // Se oculta por defecto
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(lblErrorNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        panel.add(txtApellido, gbc);

        lblErrorApellido = new JLabel("Solo se aceptan letras");
        lblErrorApellido.setForeground(Color.RED);
        lblErrorApellido.setVisible(false);  // Se oculta por defecto
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(lblErrorApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Correo electrónico:"), gbc);

        gbc.gridx = 1;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        lblErrorCorreo = new JLabel("Introduzca un correo electrónico");
        lblErrorCorreo.setForeground(Color.RED);
        lblErrorCorreo.setVisible(false);  // Se oculta por defecto
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(lblErrorCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);

        lblErrorTelefono = new JLabel("Solo se aceptan números");
        lblErrorTelefono.setForeground(Color.RED);
        lblErrorTelefono.setVisible(false);  // Se oculta por defecto
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(lblErrorTelefono, gbc);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnMostrar = new JButton("Mostrar");
        btnVolver = new JButton("Volver");

        JPanel panelBotones = new JPanel(new GridLayout(4, 1));  // Eliminar el botón de eliminar
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);

        add(panel, BorderLayout.CENTER);

        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    MostrarContactos mostrarScreen = new MostrarContactos(userNick);
                    mostrarScreen.setVisible(true);
                    dispose();
                });
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarContacto();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarContacto();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    RegistroUsuarioScreen registroScreen = new RegistroUsuarioScreen();
                    registroScreen.setVisible(true);
                    dispose();
                });
            }
        });
    }

    private void agregarContacto() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();

        boolean valid = true;

        // Validación de nombre
        if (!nombre.matches("[a-zA-Z]+")) {
            lblErrorNombre.setVisible(true);
            valid = false;
        } else {
            lblErrorNombre.setVisible(false);
        }

        // Validación de apellido
        if (!apellido.matches("[a-zA-Z]+")) {
            lblErrorApellido.setVisible(true);
            valid = false;
        } else {
            lblErrorApellido.setVisible(false);
        }

        // Validación de correo
        if (!correo.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            lblErrorCorreo.setVisible(true);
            valid = false;
        } else {
            lblErrorCorreo.setVisible(false);
        }

        // Validación de teléfono
        if (!telefono.matches("[0-9]+")) {
            lblErrorTelefono.setVisible(true);
            valid = false;
        } else {
            lblErrorTelefono.setVisible(false);
        }

        if (!valid) {
            JOptionPane.showMessageDialog(this, "Por favor, corrige los errores.");
            return;
        }

        String query = "INSERT INTO contactos (nombre, apellido, correo_electronico, telefono, nick) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correo);
            statement.setString(4, telefono);
            statement.setString(5, userNick);

            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.");
            limpiarCampos();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar el contacto: " + ex.getMessage());
        }
    }

    private void modificarContacto() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return;
        }

        String query = "UPDATE contactos SET nombre = ?, apellido = ?, correo_electronico = ?, telefono = ? WHERE nick = ? AND nombre = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correo);
            statement.setString(4, telefono);
            statement.setString(5, userNick);
            statement.setString(6, nombre);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Contacto modificado correctamente.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar el contacto: " + ex.getMessage());
        }
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String userNick = "Pepino";  // Este valor puede ser reemplazado por un valor dinámico de usuario
            CasillerosScreen casillerosScreen = new CasillerosScreen(userNick);
            casillerosScreen.setVisible(true);
        });
    }
}
