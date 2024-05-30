package org.example.Clases;



import org.example.Clases.Driver;

import java.awt.*;

public class Team {
    //atributos
    private String nombre;
    private Color team_color;
    private Driver primer_piloto;
    private Driver segundo_piloto;

    public Team(String nombre, String team_color, Driver primer_piloto, Driver segundo_piloto) {
        this.nombre = nombre;
        this.team_color = Color.decode(team_color);
        this.primer_piloto = primer_piloto;
        this.segundo_piloto = segundo_piloto;
    }

    public Team() {
        nombre="";
        team_color=null;
        primer_piloto= new Driver();
        segundo_piloto= new Driver();
    }

    public String getNombre() {
        return nombre;
    }

    public Color getTeam_color() {
        return team_color;
    }

    public Driver getPrimer_piloto() {
        return primer_piloto;
    }

    public Driver getSegundo_piloto() {
        return segundo_piloto;
    }

    public void setSegundo_piloto(Driver segundo_piloto) {
        this.segundo_piloto = segundo_piloto;
    }

    public void setPrimer_piloto(Driver primer_piloto) {
        this.primer_piloto = primer_piloto;
    }

    /*@Override
    public String toString2() {
        return nombre;
    }*/

    @Override
    public String toString() {
        return "\n Equipo: "+ nombre +
                "\n Primer piloto=" + primer_piloto +
                "\n Segundo piloto" + segundo_piloto;
    }
}
