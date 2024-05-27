package org.example.Clases;

import org.example.Interfaz.IManejoDeCampeonatos;

import java.util.*;

public class Campeonato implements IManejoDeCampeonatos {
    private List<Team> equipos;
    private List<Circuit> circuitos;
    private Map<Driver, Integer> puntos;
    private Map<Driver, Double> mejoresTiempos;
    private int totalCarreras;

    private static final double AJUSTE = 0.8; //ajuste para dar mas posibilidades d ganar a un piloto q ya haya ganado

    public Campeonato(List<Team> equipos, List<Circuit> circuitos) {
        this.equipos = equipos;
        this.circuitos = circuitos;
        this.puntos = new HashMap<>();
        this.mejoresTiempos = new HashMap<>();
        for (Team equipo : equipos) {
            puntos.put(equipo.getPrimer_piloto(), 0);
            puntos.put(equipo.getSegundo_piloto(), 0);
        }
        this.totalCarreras = circuitos.size(); //establece el numero total de carreras
    }

    public String simularCameponato() {
        StringBuilder campeonatoResultado = new StringBuilder();
        Map<Driver, Integer> puntosTotales = new HashMap<>();
        for (Circuit circuito : circuitos) {
            Map<Driver, Integer> puntosCarrera = simularCarrera(circuito);
            campeonatoResultado.append("Tabla de posiciones en: ").append(circuito.getNombre()).append(":\n\n");
            campeonatoResultado.append(generarTablaPosicionesCarrera(puntosCarrera)).append("\n");
            actualizarPuntosTotales(puntosTotales, puntosCarrera);
        }
        campeonatoResultado.append("\nTabla de posiciones final del campeonato: \n\n");
        campeonatoResultado.append(generarTablaPosiciones(puntosTotales));


        return campeonatoResultado.toString();
    }

    public Map<Driver, Integer> simularCarrera(Circuit circuito) {
        Random random = new Random();
        List<Team> equiposEnCarrera = new ArrayList<>(equipos);
        Collections.shuffle(equiposEnCarrera);

        Map<Driver, Double> tiemposDeVuelta = new HashMap<>();
        for (Team equipo : equiposEnCarrera) {
            double tiempoDeVueltaPiloto1 = random.nextDouble() * 100 + 60;
            double tiempoDeVueltaPiloto2 = random.nextDouble() * 100 + 60;

            if (puntos.get(equipo.getPrimer_piloto()) > 0) {
                tiempoDeVueltaPiloto1 *= AJUSTE;
            }
            if (puntos.get(equipo.getSegundo_piloto()) > 0) {
                tiempoDeVueltaPiloto2 *= AJUSTE;
            }

            tiemposDeVuelta.put(equipo.getPrimer_piloto(), tiempoDeVueltaPiloto1);
            tiemposDeVuelta.put(equipo.getSegundo_piloto(), tiempoDeVueltaPiloto2);
            actualizarMejorTiempo(equipo.getPrimer_piloto(), tiempoDeVueltaPiloto1);
            actualizarMejorTiempo(equipo.getSegundo_piloto(), tiempoDeVueltaPiloto2);
        }

        List<Driver> pilotosEnCarrera = new ArrayList<>(tiemposDeVuelta.keySet());
        pilotosEnCarrera.sort(Comparator.comparingDouble(tiemposDeVuelta::get));

        asignarPuntos(pilotosEnCarrera);
        return puntos;
    }

    public String generarTablaPosicionesCarrera(Map<Driver, Integer> puntosCarrera) {
        StringBuilder tabla = new StringBuilder();
        List<Driver> pilotosOrdenadosPorPuntos = new ArrayList<>(puntosCarrera.keySet());
        pilotosOrdenadosPorPuntos.sort(Comparator.comparingInt(puntosCarrera::get).reversed());
        for (int i = 0; i < pilotosOrdenadosPorPuntos.size(); i++) {
            Driver piloto = pilotosOrdenadosPorPuntos.get(i);
            tabla.append(String.format("%d. %s - Mejor Tiempo de Vuelta: %.2f - Puntos: %d\n", i + 1, piloto.getFull_name(), mejoresTiempos.get(piloto), puntosCarrera.get(piloto)));
        }
        return tabla.toString();
    }

    public String generarTablaPosiciones(Map<Driver, Integer> puntosTotales) {
        StringBuilder tabla = new StringBuilder();
        List<Driver> pilotosOrdenadosPorPuntos = new ArrayList<>(puntosTotales.keySet());
        pilotosOrdenadosPorPuntos.sort(Comparator.comparingInt(puntosTotales::get).reversed());
        for (int i = 0; i < pilotosOrdenadosPorPuntos.size(); i++) {
            Driver piloto = pilotosOrdenadosPorPuntos.get(i);
            tabla.append(String.format("%d. %s - Puntos totales: %d%n", i + 1, piloto.getFull_name(), puntosTotales.get(piloto)));
        }

        return tabla.toString();
    }

    public void asignarPuntos(List<Driver> pilotosEnCarrera) {
        int[] puntosPorPosicion = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};
        for (int i = 0; i < Math.min(pilotosEnCarrera.size(), 10); i++) {
            Driver piloto = pilotosEnCarrera.get(i);
            int puntosActuales = puntos.get(piloto);
            puntos.put(piloto, puntosActuales + puntosPorPosicion[i]);
        }
    }

    public void actualizarPuntosTotales(Map<Driver, Integer> puntosTotales, Map<Driver, Integer> puntosCarrera)
    {
        for (Map.Entry<Driver, Integer> entry : puntosCarrera.entrySet()) {
            Driver piloto = entry.getKey();
            int puntosCarreraPiloto = entry.getValue();
            int puntosTotalesPiloto = puntosTotales.getOrDefault(piloto, 0);
            puntosTotales.put(piloto, puntosTotalesPiloto + puntosCarreraPiloto);
        }
    }

    public void actualizarMejorTiempo(Driver piloto, double tiempoVuelta)
    {
        double mejorTiempo = mejoresTiempos.getOrDefault(piloto, Double.MAX_VALUE);
        if (tiempoVuelta < mejorTiempo) {
            mejoresTiempos.put(piloto, tiempoVuelta);
        }
    }

}