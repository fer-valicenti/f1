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

    @Override
    public String toString() {
        return
                "\n Equipo= " + nombre + '\'' +
                "\n Piloto 1=" + primer_piloto +
                "\n Piloto 2=" + segundo_piloto;
    }
}
