package com.mycompany.agendabasica2parcial; 
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

    
}
