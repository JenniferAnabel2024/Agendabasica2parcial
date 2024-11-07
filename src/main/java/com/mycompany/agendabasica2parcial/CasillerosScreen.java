package com.mycompany.agendabasica2parcial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CasillerosScreen extends JFrame {

    private JTextField txtNombre, txtApellido, txtCorreo, txtTelefono;
    private JButton btnAgregar, btnModificar, btnEliminar, btnMostrar, btnVolver;
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

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        panel.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Correo electrónico:"), gbc);

        gbc.gridx = 1;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnMostrar = new JButton("Mostrar");
        btnVolver = new JButton("Volver");

        JPanel panelBotones = new JPanel(new GridLayout(5, 1));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnVolver);

        gbc.gridx = 0;
        gbc.gridy = 4;
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

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarContacto();
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

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
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
            // Si no se encontraron filas afectadas, no mostramos el mensaje
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar el contacto: " + ex.getMessage());
        }
        limpiarCampos();
    }

    private void eliminarContacto() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();

        String query = "DELETE FROM contactos WHERE nick = ? AND nombre = ? AND apellido = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, userNick);
            statement.setString(2, nombre);
            statement.setString(3, apellido);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Contacto eliminado correctamente.");
            }
            // Si no se encontraron filas afectadas, no mostramos el mensaje
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el contacto: " + ex.getMessage());
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
