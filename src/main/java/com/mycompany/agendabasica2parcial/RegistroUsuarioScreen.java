package com.mycompany.agendabasica2parcial;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroUsuarioScreen extends JFrame {

    private JTextField txtNombre, txtApellido, txtCorreo, txtNick;
    private JPasswordField txtContrasena;
    private JButton btnConfirmarRegistro, btnVolver;

    public RegistroUsuarioScreen() {
        setTitle("Registro de Nuevo Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel principal con GridLayout para los campos de entrada
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        
        // Etiquetas y campos de texto
        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Nick:"));
        txtNick = new JTextField();
        panel.add(txtNick);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        // Crear botón para confirmar el registro
        btnConfirmarRegistro = new JButton("Confirmar Registro");
        btnConfirmarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        // Crear botón "Volver"
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAAutenticacion(); // Método para volver a la pantalla de autenticación
            }
        });

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConfirmarRegistro);
        panelBotones.add(btnVolver);

        // Añadir los componentes al JFrame
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para registrar al usuario (solo ejemplo)
    private void registrarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String correo = txtCorreo.getText();
        String nick = txtNick.getText();
        String contrasena = new String(txtContrasena.getPassword());

        // Aquí agregarías la lógica para guardar el usuario en la base de datos
        JOptionPane.showMessageDialog(this, "Usuario registrado con éxito:\n" +
                "Nombre: " + nombre + "\nNick: " + nick);

        // Cierra la ventana después de completar el registro
        dispose();
    }

    // Método para volver a la pantalla de autenticación
    private void volverAAutenticacion() {
        dispose(); // Cierra la ventana de registro
        new AutenticacionScreen().setVisible(true); // Muestra la ventana de autenticación
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistroUsuarioScreen().setVisible(true));
    }
}
