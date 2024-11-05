package com.mycompany.agendabasica2parcial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_base_datos";
    private static final String USER = "usuario"; // Cambia por tu usuario
    private static final String PASSWORD = "contraseña"; // Cambia por tu contraseña

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return connection;
    }
}

