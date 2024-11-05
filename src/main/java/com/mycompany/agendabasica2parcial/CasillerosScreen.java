package com.mycompany.agendabasica2parcial;
import javax.swing.*;
import java.awt.*;

public class CasillerosScreen extends JFrame {

    private JTextField txtNombre, txtApellido, txtCorreo;
    private JButton btnAgregar, btnModificar, btnEliminar, btnMostrar, btnVolver;

    public CasillerosScreen() {
        initComponents();
        setTitle("Formulario de Registro");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Panel principal
        JPanel panel = new JPanel(new GridLayout(5, 2)); // 5 filas para acomodar todos los botones

        // Etiquetas y campos de texto
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre = new JTextField(20));
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido = new JTextField(20));
        panel.add(new JLabel("Correo electrónico:"));
        panel.add(txtCorreo = new JTextField(20));

        // Botones
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnMostrar = new JButton("Mostrar");

        // Agregando botones al panel
        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnMostrar);

        // Panel para el botón "Volver"
        JPanel panelBotones = new JPanel(new BorderLayout());
        btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver, BorderLayout.CENTER);

        // Agregar paneles al JFrame
        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CasillerosScreen().setVisible(true));
    }
}