package org.example.Clases;

public abstract class Competidor {
    private String nombre;

    public Competidor(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
