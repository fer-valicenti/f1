package org.example.Clases;

import org.example.Clases.Circuit;
import org.example.Clases.Driver;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Interfaz extends JFrame {

    private JComboBox<Driver> driverComboBox;
    private JComboBox<Circuit> circuitComboBox;
    private JButton startButton, selectDriverButton, createDriverButton, selectCircuitButton;
    private List<Driver> driverList;
    private List<Circuit> circuitList;
    private JLabel backgroundLabel;
    private Driver selectedDriver;
    private Circuit selectedCircuit;
    private Clip clip;


    public Interfaz(List<Driver> driverList, List<Circuit> circuitList) {
        this.driverList = driverList;
        this.circuitList = circuitList;

        setTitle("F1 GAME");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        // Configuración del fondo
        ImageIcon backgroundIcon = new ImageIcon("src/main/java/org/example/imag/f1-24-game.jpg"); // Ruta a tu imagen de fondo
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setSize(800, 600);
        setContentPane(backgroundLabel);

        //musica
        PlayMusic("src/main/java/org/example/music/TEMA OFICIAL FORMULA 1 2018 - By Brian Tyler.wav");


        // Botón de Iniciar Juego
        startButton = new JButton("Iniciar Juego");
        startButton.setBounds(320, 200, 150, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        add(startButton);

        setVisible(true);
    }

    public void iniciarJuego() {
        // Ocultar el botón de iniciar juego
        startButton.setVisible(false);

        // Botón de Seleccionar Piloto
        selectDriverButton = new JButton("Seleccionar Piloto");
        selectDriverButton.setBounds(320, 310, 150, 30);
        selectDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarPiloto();
            }
        });
        add(selectDriverButton);

        // Botón de Crear Piloto
        createDriverButton = new JButton("Crear Piloto");
        createDriverButton.setBounds(320, 360, 150, 30);
        createDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPiloto();
            }
        });
        add(createDriverButton);

        // Botón de Seleccionar Circuito
        selectCircuitButton = new JButton("Seleccionar Circuito");
        selectCircuitButton.setBounds(320, 410, 150, 30);
        selectCircuitButton.setEnabled(false);
        selectCircuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarCircuito();
            }
        });
        add(selectCircuitButton);

        repaint();
    }

    private void seleccionarPiloto() {
        // Ventana para seleccionar piloto
        JDialog selectDriverDialog = new JDialog(this, "Seleccionar Piloto", true);
        selectDriverDialog.setSize(400, 300);
        selectDriverDialog.setLayout(null);

        JLabel driverLabel = new JLabel("Seleccione su piloto: ");
        driverLabel.setBounds(20, 20, 150, 25);
        selectDriverDialog.add(driverLabel);

        driverComboBox = new JComboBox<>(driverList.toArray(new Driver[0]));
        driverComboBox.setBounds(180, 20, 200, 25);
        selectDriverDialog.add(driverComboBox);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.setBounds(150, 200, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDriver = (Driver) driverComboBox.getSelectedItem();
                selectDriverDialog.dispose();
                selectCircuitButton.setEnabled(true); // Habilitar selección de circuito después de seleccionar un piloto
            }
        });
        selectDriverDialog.add(selectButton);

        selectDriverDialog.setVisible(true);
    }

    private void crearPiloto() {
        // Ventana para crear piloto
        JDialog createDriverDialog = new JDialog(this, "Crear Piloto", true);
        createDriverDialog.setSize(400, 300);
        createDriverDialog.setLayout(null);

        JLabel nameLabel = new JLabel("Nombre del piloto: ");
        nameLabel.setBounds(20, 20, 150, 25);
        createDriverDialog.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(180, 20, 200, 25);
        createDriverDialog.add(nameField);

        JButton createButton = new JButton("Crear");
        createButton.setBounds(150, 200, 100, 30);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nameField.getText();
                if (!nombre.isEmpty()) {
                    Driver newDriver = new Driver();
                    driverList.add(newDriver);
                    driverComboBox.addItem(newDriver);
                    selectedDriver = newDriver; // Seleccionar automáticamente el nuevo piloto creado
                    createDriverDialog.dispose();
                    selectCircuitButton.setEnabled(true); // Habilitar selección de circuito después de crear un piloto
                }
            }
        });
        createDriverDialog.add(createButton);

        createDriverDialog.setVisible(true);
    }

    private void seleccionarCircuito() {
        // Ventana para seleccionar circuito
        JDialog selectCircuitDialog = new JDialog(this, "Seleccionar Circuito", true);
        selectCircuitDialog.setSize(400, 300);
        selectCircuitDialog.setLayout(null);

        JLabel circuitLabel = new JLabel("Seleccione el circuito: ");
        circuitLabel.setBounds(20, 20, 150, 25);
        selectCircuitDialog.add(circuitLabel);

        circuitComboBox = new JComboBox<>(circuitList.toArray(new Circuit[0]));
        circuitComboBox.setBounds(180, 20, 200, 25);
        selectCircuitDialog.add(circuitComboBox);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.setBounds(150, 200, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCircuit = (Circuit) circuitComboBox.getSelectedItem();
                selectCircuitDialog.dispose();
            }
        });
        selectCircuitDialog.add(selectButton);

        selectCircuitDialog.setVisible(true);
    }

    private void PlayMusic(String filePath){

        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }

    /*
    private void empezarCarrera()
    {
        JButton empezarCarreraButton = new JButton("Empezar Carrera");
        empezarCarreraButton.setBounds(320, 460, 150, 30);
        empezarCarreraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carrera(selectedDriver, selectedCircuit);
                JOptionPane.showMessageDialog(null, "Carrera en marcha!");
            }
        });
    }
    private String carrera(Driver driver, Circuit circuit)
    {
        Random random = new Random();

        int totalLaps = 60; //numero total de vueltas en la carrera
        int[] posiciones = new int[driverList.size()]; // almacena las posisiones de los pilotos
        int[] tiemposDeVuelta = new int[driverList.size()]; //almacena los tiempos de vuelta d los pilotos

        for(int vuelta = 1; vuelta <= totalLaps; vuelta++)
        {
            for(int i=0; i < driverList.size(); i++)
            {
                if(random.nextDouble() < calcularOvertake(driverList.get(i))) //si el piloto tiene exito en superar a otro
                {
                    int nuevaPosicion = Math.max(0, posiciones[i] - 1); //mover hacia adelante en la pista
                    posiciones[i] = nuevaPosicion;
                }

                tiemposDeVuelta[i] += calcularTiempoDeVuelta(driverList.get(i), circuit);
            }
        }

        int ganador=0;
        for(int i=0; i < tiemposDeVuelta.length; i++)
        {
            if(tiemposDeVuelta[i] < tiemposDeVuelta[ganador])
            {
                ganador=1;
            }
        }
        return "Ganador: " + driverList.get(ganador) + "\nTiempo total: " + tiemposDeVuelta[ganador] + "\nEn el circuito: " + circuit.getNombre();
    }

    private double calcularOvertake(Driver driver)
    {
        return 0.5;
    }

    private int calcularTiempoDeVuelta(Driver driver, Circuit circuit)
    {
        Random random = new Random();
        int tiempoMinimo=60;
        int tiempoMaximo=120;
        return tiempoMinimo + random.nextInt(tiempoMaximo - tiempoMinimo +1);
    }


     */
}

