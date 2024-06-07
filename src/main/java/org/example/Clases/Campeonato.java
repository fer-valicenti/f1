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

    /**
       Simula todo el campeonato iterando sobre cada circuito
        simulando carreras, actualizando puntos y generando resultados.
         Devuelve una cadena que contiene los resultados del campeonato.

     */

    public String simularCameponato() {

        StringBuilder campeonatoResultado = new StringBuilder();
        Map<Driver, Integer> puntosTotales = new HashMap<>();
        Map<Team, Integer> puntosTotalesEquiposFinal = new HashMap<>();
        Map<Driver, Integer> puntosCarrera = new HashMap<>();

        // Iterar sobre cada circuito
        for (Circuit circuito : circuitos) {
            puntosCarrera = simularCarrera(circuito);
            campeonatoResultado.append("Tabla de posiciones en: ").append(circuito.getNombre()).append(":\n\n");
            campeonatoResultado.append(generarTablaPosicionesCarrera(puntosCarrera)).append("\n");
            Map<Team, Integer> puntosTotalesEquipos = new HashMap<>();
            actualizarPuntosTotales(puntosTotales, puntosCarrera);
            actualizarPuntosPorEquipo(puntosTotalesEquipos, puntosCarrera);
            campeonatoResultado.append(generarTablaPosicionesEquipos(puntosTotalesEquipos));
            puntosTotalesEquiposFinal = puntosTotalesEquipos;
            // Mostrar los resultados del circuito actual
            System.out.println(campeonatoResultado.toString());
            campeonatoResultado.setLength(0); // Limpiar el StringBuilder
        }

        // Mostrar la tabla de posiciones final del campeonato de pilotos
        campeonatoResultado.append("\nTabla de posiciones Final del Campeonato: \n\n");
        campeonatoResultado.append(generarTablaPosiciones(puntosCarrera));

        // Mostrar la tabla de posiciones final del campeonato de constructores
        campeonatoResultado.append("\nTabla de posiciones Final del Campeonato de Constructores: \n\n");
        campeonatoResultado.append(generarTablaPosicionesEquipos(puntosTotalesEquiposFinal));

        return campeonatoResultado.toString();

    }
    public String generarTablaFinal(Map<Driver, Integer> puntosTotales)
    {
        Map<Team, Integer> puntosTotalesEquiposFinal = new HashMap<>();
        String tablaPilotos = generarTablaPosiciones(puntosTotales);
        actualizarPuntosPorEquipo(puntosTotalesEquiposFinal, puntosTotales);
        String tablaEquipos = generarTablaPosicionesEquipos(puntosTotalesEquiposFinal);
        String tablaFinal = "\nTabla de posiciones Final del Campeonato: \n\n" +tablaPilotos+ "\nTabla de posiciones Final del Campeonato de Constructores: \n\n" + tablaEquipos;

        return tablaFinal;
    }

    /**
     * Simula una carrera para un circuito dado generando tiempos de vuelta,
     * ajustando los tiempos en función del rendimiento previo, asignando puntos
     * y devolviendo los puntos para la carrera.
     */

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



    /**
     * Genera una tabla formateada de posiciones para una sola carrera
     * basada en los puntos obtenidos por los pilotos en la carrera.
     */

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

    /**
     * Genera una tabla formateada de posiciones finales para los pilotos
     * basada en los puntos totales obtenidos a lo largo del campeonato.
     */

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


    /**
     * Genera una tabla formateada de posiciones finales para los equipos
     * basada en los puntos totales obtenidos a lo largo del campeonato.
     */

    public String generarTablaPosicionesEquipos(Map<Team, Integer> puntosTotales)
    {
        StringBuilder tabla= new StringBuilder();
        List<Team> equiposOrdenadosPorPuntos = new ArrayList<>(puntosTotales.keySet());
        equiposOrdenadosPorPuntos.sort(Comparator.comparingInt(puntosTotales::get).reversed());
        for (int i = 0; i < equiposOrdenadosPorPuntos.size(); i++)
        {
            Team equipo= equiposOrdenadosPorPuntos.get(i);
            tabla.append(String.format("%d. %s - Puntos totales: %d%n", i + 1, equipo.getNombre(), puntosTotales.get(equipo)));
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

    /**
     * Actualiza los puntos totales de los pilotos en función de los puntos
     * obtenidos en una carrera específica.
     */
    public void actualizarPuntosTotales(Map<Driver, Integer> puntosTotales, Map<Driver, Integer> puntosCarrera)
    {
        for (Map.Entry<Driver, Integer> entry : puntosCarrera.entrySet()) {
            Driver piloto = entry.getKey();
            int puntosCarreraPiloto = entry.getValue();
            int puntosTotalesPiloto = puntosTotales.getOrDefault(piloto, 0);
            puntosTotales.put(piloto, puntosTotalesPiloto + puntosCarreraPiloto);
        }
    }

    /**
     * Actualiza el mejor tiempo de vuelta de un piloto si el tiempo actual
     * es mejor que el tiempo registrado previamente.
     */
    public void actualizarMejorTiempo(Driver piloto, double tiempoVuelta)
    {
        double mejorTiempo = mejoresTiempos.getOrDefault(piloto, Double.MAX_VALUE);
        if (tiempoVuelta < mejorTiempo) {
            mejoresTiempos.put(piloto, tiempoVuelta);
        }
    }

    /**
     * Actualiza los puntos totales de los equipos en función de los puntos
     * obtenidos por sus pilotos en una carrera específica.
     */
    public void actualizarPuntosPorEquipo(Map<Team, Integer> puntosTotalesEquipos, Map<Driver, Integer> puntosCarrera)
    {
        for(Map.Entry<Driver, Integer> entry : puntosCarrera.entrySet())
        {
            Driver piloto = entry.getKey();
            Team equipo = encontrarEquipoPorPiloto(piloto);
            int puntosCarreraPiloto = entry.getValue();

            int puntosTotalesEquipo = puntosTotalesEquipos.getOrDefault(equipo, 0);
            puntosTotalesEquipos.put(equipo, puntosTotalesEquipo + puntosCarreraPiloto);
        }
    }

    public Team encontrarEquipoPorPiloto(Driver piloto)
    {
        for(Team equipo : equipos)
        {
            if(equipo.getPrimer_piloto().equals(piloto) || equipo.getSegundo_piloto().equals(piloto))
            {
                return equipo;
            }
        }
        return null; // en caso de q no se encuentre el equipo
    }

}