
package com.mycompany.agendabasica2parcial;

import javax.swing.*;

public class Agendabasica2parcial {
    
    public static void main(String[] args) {
        // Otras inicializaciones que puedas tener
        
        // Crear e iniciar la ventana de MostrarContactos
        SwingUtilities.invokeLater(() -> {
            MostrarContactos mostrarContactos = new MostrarContactos();
            mostrarContactos.setVisible(true);
        });
    }
}
