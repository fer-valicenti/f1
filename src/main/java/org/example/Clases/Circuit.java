package org.example.Clases;

public class Circuit {
    private String nombre;
    private String locacion;
    private String pais;

    public Circuit(String nombre, String locacion, String pais) {
        this.nombre = nombre;
        this.locacion = locacion;
        this.pais = pais;
    }
    public Circuit(){
        nombre="";
        locacion="";
        pais="";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
