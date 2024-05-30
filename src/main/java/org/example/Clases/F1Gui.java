package org.example.Clases;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.*;


//https://www.youtube.com/watch?v=6YYmZ8cQc7o&ab_channel=GeoCositasParaDummies

public class F1Gui extends JFrame {

    private JComboBox<Driver> driverComboBox;
    private JComboBox<Team> teamComboBox;
    private JButton startButton, selectDriverButton, createDriverButton,createCircuitButton;
    private List<Team> teams;
    private List<Driver> drivers;
    private List<Circuit> circuits;
    private Campeonato campeonato;
    private JLabel backgroundLabel;
    private Driver selectedDriver;
    private JTextArea circuitListTextArea;
    private Team selectTeam;
    private Clip clip;
    public F1Gui()
    {
        setTitle("FI GAME");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
circuits=OpenF1Client.generarListaCircuitos();
teams=OpenF1Client.generarGrilla();
drivers=OpenF1Client.generarListaPilotos();
        //fondo
        ImageIcon backgroundIcon = new ImageIcon("src/main/java/org/example/imag/f1-24-game.jpg");
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setSize(800, 600);
        setContentPane(backgroundLabel);

        //musica
        PlayMusic("src/main/java/org/example/music/TEMA OFICIAL FORMULA 1 2018 - By Brian Tyler.wav");

        //Boton iniciar juego
        startButton = new JButton("Iniciar Juego");
        startButton.setBounds(320, 360, 150, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        add(startButton);
        setVisible(true);
    }

    public void iniciarJuego()
    {
        //ocultamos el boton
        startButton.setVisible(false);

        //booton de seleccionar piloto
        selectDriverButton = new JButton("Seleccionar Piloto");
        selectDriverButton.setBounds(320, 380, 150, 30);
        selectDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarPiloto();
            }
        });
        add(selectDriverButton);

        //Boton crear piloto
        createDriverButton = new JButton("Crear Piloto");
        createDriverButton.setBounds(320, 410, 150, 30);
        createDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPiloto();
            }
        });
        add(createDriverButton);
        createDriverButton.setVisible(true);

        // Área de texto para listar circuitos
        JButton createCircuitButton = new JButton("Lista de circuitos");
        createCircuitButton.setBounds(320, 450, 150, 30);
        createCircuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCircuitos();
            }
        });
        add(createCircuitButton);
        createCircuitButton.setVisible(true);
        /*JLabel circuitLabel = new JLabel("Circuitos:");
        circuitLabel.setBounds(320, 410, 150, 30);
        add(circuitLabel);

        circuitListTextArea = new JTextArea();
        circuitListTextArea.setBounds(320, 450, 150, 80);
        circuitListTextArea.setEditable(false); // Hacer que el área de texto sea no editable
        add(circuitListTextArea);

        // Llenar el área de texto con los nombres de los circuitos
        listarCircuitos(OpenF1Client.generarListaCircuitos());

        repaint();*/
    }

    private void seleccionarPiloto()
    {
        //Ventana para seleccionar piloto
        JDialog selectDriverDialog = new JDialog(this, "Seleccionar piloto", true);
        selectDriverDialog.setSize(400, 300);
        selectDriverDialog.setLayout(null);

        JLabel driverLabel = new JLabel("Seleccione su piloto: ");
        driverLabel.setBounds(20, 20, 150, 25);
        selectDriverDialog.add(driverLabel);

        driverComboBox = new JComboBox<Driver>(drivers.toArray(new Driver[0])); //ACA HAY UN PROBLEMA
        driverComboBox.setBounds(180, 20, 200, 25);
        selectDriverDialog.add(driverComboBox);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.setBounds(150, 200, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDriver = (Driver) driverComboBox.getSelectedItem();
                selectDriverDialog.dispose();
                iniciarCampeonato(); //iniciar el campeonato desp de seleccionar un piloto
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

        JLabel firstNameLabel = new JLabel("Nombre: ");
        firstNameLabel.setBounds(20, 20, 150, 25);
        createDriverDialog.add(firstNameLabel);

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(180, 20, 200, 25);
        createDriverDialog.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Apellido: ");
        lastNameLabel.setBounds(20, 50, 150, 25);
        createDriverDialog.add(lastNameLabel);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(180, 50, 200, 25);
        createDriverDialog.add(lastNameField);

        JLabel countryLabel = new JLabel("País: ");
        countryLabel.setBounds(20, 80, 150, 25);
        createDriverDialog.add(countryLabel);

        JTextField countryField = new JTextField();
        countryField.setBounds(180, 80, 200, 25);
        createDriverDialog.add(countryField);

        JLabel numberLabel = new JLabel("Número del Auto: ");
        numberLabel.setBounds(20, 110, 150, 25);
        createDriverDialog.add(numberLabel);

        JTextField numberField = new JTextField();
        numberField.setBounds(180, 110, 200, 25);
        createDriverDialog.add(numberField);

        JButton createButton = new JButton("Crear");
        createButton.setBounds(150, 200, 100, 30);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String country = countryField.getText();
                String numberStr = numberField.getText();
                int number = 0;
                try {
                    number = Integer.parseInt(numberStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(createDriverDialog, "Error: Número de auto no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si el número de auto ya está en uso
                if (isDriverNumberUsed(number)) {
                    JOptionPane.showMessageDialog(createDriverDialog, "Error: El número de auto ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Construir el objeto Driver con los datos ingresados
                Driver newDriver = new Driver(lastName.substring(0, Math.min(lastName.length(), 3)).toUpperCase(),
                        firstName, lastName, country, number);

                // Agregar el nuevo piloto a la lista de pilotos
                drivers.add(newDriver); // Debes agregarlo a la lista de pilotos en tu aplicación

                // Imprimir los detalles del nuevo piloto creado
                JOptionPane.showMessageDialog(createDriverDialog, "Nuevo piloto creado:\n" + newDriver.toString(), "Piloto Creado", JOptionPane.INFORMATION_MESSAGE);

                createDriverDialog.dispose();
                // selectCircuitButton.setEnabled(true); // Habilitar selección de circuito después de crear un piloto
            }
            //seleccionar equipo
        });
        createDriverDialog.add(createButton);

        createDriverDialog.setVisible(true);
    }

    private void iniciarCampeonato() {
        // Crear el campeonato con el piloto seleccionado
        if (selectedDriver != null) {
            campeonato = new Campeonato(teams, circuits);
            String resultado = campeonato.simularCameponato(); // Resultado del campeonato simulado
            // Aquí puedes mostrar el resultado en una ventana de diálogo o donde prefieras
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se ha seleccionado ningún piloto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarCircuitos() {
        StringBuilder circuitosText = new StringBuilder();
        for (Circuit circuit : circuits) {
            circuitosText.append(circuit.getNombre()).append("\n");
        }
        circuitListTextArea.setText(circuitosText.toString());
    }

    private boolean isDriverNumberUsed(int number) {
        // Debes implementar la lógica para verificar si el número de auto está en uso
        // Devuelve true si el número de auto ya está asignado a otro piloto, de lo contrario, devuelve false
        for (Driver driver : drivers) { // Suponiendo que drivers es la lista de todos los pilotos
            if (driver.getDriver_number() == number) {
                return true; // El número de auto está en uso
            }
        }
        return false; // El número de auto no está en uso
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
}
