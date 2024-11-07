package com.mycompany.agendabasica2parcial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuarioScreen extends JFrame {

    private Connection connection;
    private PreparedStatement statement;

    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtNick;
    private JPasswordField txtContrasena; // Campo para la contraseña
    private JTextField txtTelefono;
    private JButton btnRegistrar;

    public RegistroUsuarioScreen() {
        // Configuración de la ventana
        setTitle("Registro de Usuario");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2)); // Cambia el layout a 7 filas y 2 columnas

        // Inicializa los campos de texto
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtNick = new JTextField();
        txtContrasena = new JPasswordField(); // Campo para la contraseña
        txtTelefono = new JTextField();

        btnRegistrar = new JButton("Registrar");

        // Agrega componentes al JFrame
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(new JLabel("Nick:"));
        add(txtNick);
        add(new JLabel("Contraseña:"));
        add(txtContrasena);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(btnRegistrar);

        // Acción del botón registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario(txtNombre.getText(), txtApellido.getText(), 
                                txtCorreo.getText(), txtNick.getText(),
                                new String(txtContrasena.getPassword()), 
                                txtTelefono.getText());
            }
        });

        setVisible(true);
    }

    public void registrarUsuario(String nombre, String apellido, String correo, String nick, String contrasena, String telefono) {
        // Validación del número de teléfono (solo dígitos y máximo 10 caracteres)
        if (!telefono.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(null, "El número de teléfono debe tener solo dígitos y no más de 10 caracteres.");
            return;
        }

        try {
            // Establece la conexión con la base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mibasededatosagenda", "root", "");

            // Prepara la consulta para insertar el nuevo usuario en la tabla 'autenticacion'
            String query = "INSERT INTO autenticacion (nombre, apellido, email, nick, contrasena, telefono) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            // Asigna los valores a los parámetros de la consulta
            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setString(3, correo);
            statement.setString(4, nick);
            statement.setString(5, contrasena);
            statement.setString(6, telefono);

            // Ejecuta la actualización
            statement.executeUpdate();

            // Mensaje de éxito
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito:\n" +
                    "Nombre: " + nombre + "\nNick: " + nick);

            // Abre CasillerosScreen en un hilo separado
            SwingUtilities.invokeLater(() -> {
                CasillerosScreen casilleros = new CasillerosScreen(nick); 
                casilleros.setVisible(true);
                dispose(); // Cierra la ventana actual
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroUsuarioScreen());
    }
}
