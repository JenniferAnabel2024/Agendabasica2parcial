package com.mycompany.agendabasica2parcial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar la ventana de contactos al inicio
        SwingUtilities.invokeLater(() -> {
            MostrarContactos mostrarContactos = new MostrarContactos();
            mostrarContactos.setVisible(true);
        });
    }
}
