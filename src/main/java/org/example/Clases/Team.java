package org.example.Clases;



import org.example.Clases.Driver;

import java.awt.*;

public class Team extends Competidor {
    //atributos
    private Color team_color;
    private Driver primer_piloto;
    private Driver segundo_piloto;

    public Team(String nombre, String team_color, Driver primer_piloto, Driver segundo_piloto) {
        super(nombre);
        this.team_color = Color.decode(team_color);
        this.primer_piloto = primer_piloto;
        this.segundo_piloto = segundo_piloto;
    }

    public Team() {
        super("");
        team_color=null;
        primer_piloto= new Driver();
        segundo_piloto= new Driver();
    }

    @Override
    public String getNombre() {
        return super.getNombre();
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


    @Override
    public String toString() {
        return "Equipo: " + getNombre();
    }


}
