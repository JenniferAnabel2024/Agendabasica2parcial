package com.mycompany.agendabasica2parcial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/mibasededatosagenda";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Si no tienes contraseña, déjalo vacío

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }
}

