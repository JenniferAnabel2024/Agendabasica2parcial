package com.mycompany.agendabasica2parcial; // Asegúrate de que el paquete sea el correcto

public class ContactosABM {
    private String nombre;
    private String telefono;
    private String email;

    // Constructor
    public ContactosABM(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    // Métodos adicionales para agregar, eliminar y modificar contactos
    // Agrega aquí tu lógica para el ABM de contactos
}
