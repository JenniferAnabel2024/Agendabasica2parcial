package com.mycompany.agendabasica2parcial;

import javax.swing.*;

public class Agendabasica2parcial {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            MostrarContactos mostrarContactos = new MostrarContactos();
            mostrarContactos.setVisible(true);
        });
    }
}
