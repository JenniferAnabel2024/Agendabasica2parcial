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

    // Etiquetas de error
    private JLabel lblErrorNombre;
    private JLabel lblErrorApellido;
    private JLabel lblErrorCorreo;
    private JLabel lblErrorNick;
    private JLabel lblErrorTelefono;

    public RegistroUsuarioScreen() {
        // Configuración de la ventana
        setTitle("Registro de Usuario");
        setSize(400, 450);  // Aumenté el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Cambié el layout a GridBagLayout para mayor flexibilidad

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Hacer que los componentes se expandan horizontalmente

        // Inicializa los campos de texto
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtNick = new JTextField();
        txtContrasena = new JPasswordField(); // Campo para la contraseña
        txtTelefono = new JTextField();

        btnRegistrar = new JButton("Registrar");

        // Inicializa las etiquetas de error
        lblErrorNombre = new JLabel("Solo se aceptan letras");
        lblErrorApellido = new JLabel("Solo se aceptan letras");
        lblErrorCorreo = new JLabel("Correo debe contener un @");
        lblErrorNick = new JLabel("Solo se aceptan letras");
        lblErrorTelefono = new JLabel("Solo números y máximo 10 dígitos");

        // Configuración de color de las etiquetas de error
        lblErrorNombre.setForeground(Color.RED);
        lblErrorApellido.setForeground(Color.RED);
        lblErrorCorreo.setForeground(Color.RED);
        lblErrorNick.setForeground(Color.RED);
        lblErrorTelefono.setForeground(Color.RED);

        lblErrorNombre.setVisible(false);
        lblErrorApellido.setVisible(false);
        lblErrorCorreo.setVisible(false);
        lblErrorNick.setVisible(false);
        lblErrorTelefono.setVisible(false);

        // Definir tamaño preferido de los JTextField
        txtNombre.setPreferredSize(new Dimension(200, 30));
        txtApellido.setPreferredSize(new Dimension(200, 30));
        txtCorreo.setPreferredSize(new Dimension(200, 30));
        txtNick.setPreferredSize(new Dimension(200, 30));
        txtTelefono.setPreferredSize(new Dimension(200, 30));
        txtContrasena.setPreferredSize(new Dimension(200, 30));

        // Agrega los componentes al JFrame utilizando GridBagLayout
        // Fila 1: Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas para el texto
        add(txtNombre, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(lblErrorNombre, gbc);

        // Fila 2: Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(txtApellido, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(lblErrorApellido, gbc);

        // Fila 3: Correo
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(txtCorreo, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(lblErrorCorreo, gbc);

        // Fila 4: Nick
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Nick:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(txtNick, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(lblErrorNick, gbc);

        // Fila 5: Contraseña
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(txtContrasena, gbc);

        // Fila 6: Teléfono
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        add(txtTelefono, gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        add(lblErrorTelefono, gbc);

        // Fila 7: Botón registrar
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3; // Hacer que el botón ocupe todo el ancho
        add(btnRegistrar, gbc);

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

        // Centrar la ventana
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void registrarUsuario(String nombre, String apellido, String correo, String nick, String contrasena, String telefono) {
        boolean valid = true;

        // Validación de nombre (solo letras)
        if (!nombre.matches("[a-zA-Z]+")) {
            lblErrorNombre.setVisible(true);
            valid = false;
        } else {
            lblErrorNombre.setVisible(false);
        }

        // Validación de apellido (solo letras)
        if (!apellido.matches("[a-zA-Z]+")) {
            lblErrorApellido.setVisible(true);
            valid = false;
        } else {
            lblErrorApellido.setVisible(false);
        }

        // Validación de correo (debe contener "@")
        if (!correo.contains("@")) {
            lblErrorCorreo.setVisible(true);
            valid = false;
        } else {
            lblErrorCorreo.setVisible(false);
        }

        // Validación de nick (solo letras)
        if (!nick.matches("[a-zA-Z]+")) {
            lblErrorNick.setVisible(true);
            valid = false;
        } else {
            lblErrorNick.setVisible(false);
        }

        // Validación de teléfono (solo números y máximo 10 caracteres)
        if (!telefono.matches("\\d{1,10}")) {
            lblErrorTelefono.setVisible(true);
            valid = false;
        } else {
            lblErrorTelefono.setVisible(false);
        }

        if (!valid) {
            JOptionPane.showMessageDialog(null, "Por favor, corrige los errores.");
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
            JOptionPane.showMessageDialog(null, "Error en el registro: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new RegistroUsuarioScreen();
    }
}
