package org.example.Interfaz;

import org.example.Clases.Circuit;
import org.example.Clases.Driver;
import org.example.Clases.Team;

import java.util.List;
import java.util.Map;

public interface IManejoDeCampeonatos {

    public String simularCameponato();
    public Map<Driver, Integer> simularCarrera(Circuit circuito);
    public String generarTablaPosicionesCarrera(Map<Driver, Integer> puntosCarrera);
    public String generarTablaPosiciones(Map<Driver, Integer> puntosTotales);
    public void asignarPuntos(List<Driver> pilotosEnCarrera);
    public void actualizarPuntosTotales(Map<Driver, Integer> puntosTotales, Map<Driver, Integer> puntosCarrera);
    public void actualizarMejorTiempo(Driver piloto, double tiempoVuelta);
}
