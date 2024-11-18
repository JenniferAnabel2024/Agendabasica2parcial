package com.mycompany.agendabasica2parcial;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Crear y mostrar la ventana de autenticación al inicio
        //SwingUtilitieses una clase de utilidad de Swing que proporciona varios métodos 
        SwingUtilities.invokeLater(() -> {
            AutenticacionScreen autenticacionScreen = new AutenticacionScreen();
            autenticacionScreen.setVisible(true);
        });
    }
}
