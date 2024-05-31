package org.example.Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;


//https://youtu.be/mGkrbzJZCoE

public class F1Gui extends JFrame {

    private JComboBox<Driver> driverComboBox;
    private JComboBox<Team> teamComboBox;
    private JButton startButton,restartButton, selectDriverButton, createDriverButton,createCircuitButton,selectChangeDriverButton,selectChangeDriverButton2, iniciarCampeonatoButton, siguienteButton, correrGranPrixButton;
    private List<Team> teams;
    private List<Driver> drivers;
    private List<Circuit> circuits;
    private Campeonato campeonato;
    private JLabel backgroundLabel;
    private Driver selectedDriver;
    private Team selectedTeam;
    private Clip clip;
    private JTextArea resultadosTextArea,textArea;
    private JScrollPane scrollPane;

    public static int circuitoActual = 0; //Declarar circuitoActual como un campo de clase

    public static int numeroPartida = 1;
    public F1Gui()
    {
        setTitle("FI GAME");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        circuits=OpenF1Client.generarListaCircuitos();
        teams=OpenF1Client.generarGrilla();
        drivers=OpenF1Client.generarListaPilotos(teams);

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
        createDriverButton.setBounds(320, 420, 150, 30);
        createDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPiloto();
            }
        });
        add(createDriverButton);
        createDriverButton.setVisible(true);

        // Boton lista de circuitos
        createCircuitButton = new JButton("Lista de circuitos");
        createCircuitButton.setBounds(320, 460, 150, 30);
        createCircuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCircuitos();
            }
        });
        add(createCircuitButton);

        restartButton = new JButton("Salir");
        restartButton.setBounds(320, 500, 150, 30);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        add(restartButton);

        setVisible(true);
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

        driverComboBox = new JComboBox<Driver>(drivers.toArray(new Driver[0]));
        driverComboBox.setBounds(180, 20, 200, 25);
        selectDriverDialog.add(driverComboBox);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.setBounds(150, 200, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDriver = (Driver) driverComboBox.getSelectedItem();
                selectDriverDialog.dispose();
                mostrarBotonIniciarCampeonato();
                startButton.setVisible(false);
                selectDriverButton.setVisible(false);
                createDriverButton.setVisible(false);
                createCircuitButton.setVisible(false);
             }
        });
        selectDriverDialog.add(selectButton);

        selectDriverDialog.setVisible(true);
    }

    private void mostrarBotonIniciarCampeonato()
    {
        if(selectedDriver != null)
        {
            iniciarCampeonatoButton = new JButton("¡Iniciar Campeonato!");
            iniciarCampeonatoButton.setBounds(320, 400, 150, 30);
            iniciarCampeonatoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    iniciarCampeonato();
                }
            });
            add(iniciarCampeonatoButton);

            correrGranPrixButton = new JButton("¡Correr Grand Prix!");
            correrGranPrixButton.setBounds(320, 440, 150, 30);
            correrGranPrixButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    correrGrandPrix();
                }
            });
            add(correrGranPrixButton);


            repaint();
        }
    }

    private void crearPiloto() {
        // Ventana para crear piloto
        JDialog createDriverDialog = new JDialog(this, "Crear Piloto", true);
        createDriverDialog.setSize(400, 400);
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
        createButton.setBounds(150, 300, 100, 30);
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
                //drivers.add(newDriver); // Debes agregarlo a la lista de pilotos en tu aplicación

                // Imprimir los detalles del nuevo piloto creado
                JOptionPane.showMessageDialog(createDriverDialog, "Nuevo piloto creado:\n" + newDriver.toString(), "Piloto Creado", JOptionPane.INFORMATION_MESSAGE);

                createDriverDialog.dispose();
                seleccionarEquipo(newDriver);
                drivers=OpenF1Client.generarListaPilotos(teams);
                // selectCircuitButton.setEnabled(true); // Habilitar selección de circuito después de crear un piloto
            }
            //seleccionar equipo
        });
        createDriverDialog.add(createButton);

        createDriverDialog.setVisible(true);
    }

    private void iniciarCampeonato() {
       iniciarCampeonatoButton.setVisible(false);
       campeonato = new Campeonato(teams, circuits);

       resultadosTextArea = new JTextArea();
        resultadosTextArea.setEditable(false);

        scrollPane = new JScrollPane(resultadosTextArea);
       scrollPane.setBounds(20, 20, 760, 300);
       add(scrollPane);


       siguienteButton = new JButton("Siguiente Carrera");
       siguienteButton.setBounds(320, 400, 150, 30);
       siguienteButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               mostrarResultadoCircuitoActual();
           }
       });
       add(siguienteButton);

        // Ocultar el botón de correr Grand Prix
        if(correrGranPrixButton != null)
       {
           correrGranPrixButton.setVisible(false);
       }

       mostrarResultadoCircuitoActual();

    }
    private void mostrarResultadoCircuitoActual()
    {
        if(circuitoActual < circuits.size())
        {
            Circuit circuito = circuits.get(circuitoActual);

            ImageIcon loadingIcon = new ImageIcon("src/main/java/org/example/imag/gif next race.gif");
            JLabel loadingLabel = new JLabel(loadingIcon);
            loadingLabel.setBounds(320, 430, loadingIcon.getIconWidth(), loadingIcon.getIconHeight());
            add(loadingLabel);
            loadingLabel.setVisible(true);

            repaint();

            // Simular retraso para mostrar la animación
            try {
                Thread.sleep(700); // Puedes ajustar la duración según sea necesario
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Quitar la animación GIF
            loadingLabel.setVisible(false);

            String nombreCircuito = circuito.getNombre();
            String resultadoCircuito = nombreCircuito + "\n\n" + campeonato.generarTablaPosicionesCarrera(campeonato.simularCarrera(circuito));

            resultadosTextArea.setText(resultadoCircuito);
            resultadosTextArea.setCaretPosition(0);

            //para que la barra comience de arriba
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    scrollPane.getViewport().setViewPosition(new Point(0, 0));
                }
            });

            circuitoActual++;

            if(circuitoActual >= circuits.size()) {
                siguienteButton.setText("Ver Resultado Final");
                siguienteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarResultadoFinal();
                        siguienteButton.setEnabled(false);
                    }
                });
            }
            guardarResultadosEnArchivo(resultadoCircuito);
        }
    }

    private void mostrarResultadoFinal()
    {
        siguienteButton.setVisible(false);
        String resultadoFinal = campeonato.simularCameponato();
        resultadosTextArea.setText(resultadoFinal);
        resultadosTextArea.setCaretPosition(0);
        guardarPartida(numeroPartida, resultadoFinal);
        numeroPartida++;
    }

    private void listarCircuitos() {
        StringBuilder circuitosText = new StringBuilder();
        for (Circuit circuit : circuits) {
            circuitosText.append(circuit.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, circuitosText.toString(), "Lista de Circuitos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void correrGrandPrix() {

        // Ocultar el botón de Iniciar Campeonato
        if (iniciarCampeonatoButton != null) {
            iniciarCampeonatoButton.setVisible(false);
        }

        if (selectedDriver != null) {
            // Mostrar cuadro de diálogo para seleccionar circuito
            String[] circuitNames = new String[circuits.size()];
            for (int i = 0; i < circuits.size(); i++) {
                circuitNames[i] = circuits.get(i).getNombre();
            }
            String selectedCircuitName = (String) JOptionPane.showInputDialog(this, "Selecciona un circuito:", "Seleccionar Circuito", JOptionPane.QUESTION_MESSAGE, null, circuitNames, circuitNames[0]);

            // Buscar el circuito seleccionado
            Circuit selectedCircuit = null;
            for (Circuit circuit : circuits) {
                if (circuit.getNombre().equals(selectedCircuitName)) {
                    selectedCircuit = circuit;
                    break;
                }
            }

            if (selectedCircuit != null) {
                // Simular carrera en el circuito seleccionado
                List<Circuit> circuito= new ArrayList<>();
                circuito.add(selectedCircuit);

                campeonato = new Campeonato(teams,circuito);
                String nombreCircuito = selectedCircuit.getNombre();
                String resultadoCircuito = nombreCircuito + "\n\n" + campeonato.generarTablaPosicionesCarrera(campeonato.simularCarrera(selectedCircuit));
                mostrarTablaResultado(resultadoCircuito, "Resultado del Gran Prix");
                guardarPartida(numeroPartida, resultadoCircuito);
                numeroPartida++;
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un circuito", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un piloto primero", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarTablaResultado(String resultado, String titulo) {
        JFrame parentFrame = (JFrame) SwingUtilities.getRoot(this); // Obtenemos el JFrame principal
        textArea = new JTextArea(resultado);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Creamos un JDialog modal que muestre la tabla de resultados
        JDialog dialog = new JDialog(parentFrame, titulo, true);
        dialog.getContentPane().add(scrollPane);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame); // Aparece en el centro del JFrame principal
        dialog.setVisible(true);
    }

    private void reiniciarJuego() {
        getContentPane().removeAll(); // Elimina todos los componentes de la ventana actual
        new F1Gui(); // Crea una nueva instancia de F1Gui, reiniciando así el juego
        revalidate(); // Vuelve a pintar la ventana para mostrar los cambios
        repaint();
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
    private void seleccionarEquipo(Driver nuevoPiloto){
        JDialog selectTeamDialog = new JDialog(this, "Seleccionar Escuderia", true);
        selectTeamDialog.setSize(400, 300);
        selectTeamDialog.setLayout(null);
        JLabel teamLabel = new JLabel("Seleccione su escuderia: ");
        teamLabel.setBounds(20, 20, 150, 25);
        selectTeamDialog.add(teamLabel);

        teamComboBox = new JComboBox<Team>(teams.toArray(new Team[0]));
        teamComboBox.setBounds(180, 20, 200, 25);
        selectTeamDialog.add(teamComboBox);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.setBounds(150, 200, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTeam = (Team) teamComboBox.getSelectedItem();
                selectTeamDialog.dispose();
                reemplazarPiloto(selectedTeam,nuevoPiloto);

            }
        });
        selectTeamDialog.add(selectButton);

        selectTeamDialog.setVisible(true);
    }

    private void reemplazarPiloto(Team equipo,Driver nuevoPiloto){
        JDialog selectTeamDialog = new JDialog(this, "Seleccionar Piloto a reemplazar", true);
        selectTeamDialog.setSize(400, 300);
        selectTeamDialog.setLayout(null);
        JLabel teamLabel = new JLabel("Seleccione el piloto a reemplazar: ");
        teamLabel.setBounds(20, 20, 300, 25);
        selectTeamDialog.add(teamLabel);

        //booton de seleccionar piloto
        selectChangeDriverButton = new JButton(equipo.getPrimer_piloto().getFull_name());
        selectChangeDriverButton.setBounds(50, 60, 150, 30);
        selectChangeDriverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipo.setPrimer_piloto(nuevoPiloto);
                selectTeamDialog.dispose();
            }
        });
        selectTeamDialog.add(selectChangeDriverButton);
        selectChangeDriverButton2 = new JButton(equipo.getSegundo_piloto().getFull_name());
        selectChangeDriverButton2.setBounds(200, 60, 150, 30);
        selectChangeDriverButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                equipo.setSegundo_piloto(nuevoPiloto);
                selectTeamDialog.dispose();
            }
        });
        selectTeamDialog.add(selectChangeDriverButton2);

        selectTeamDialog.setVisible(true);

    }


    //ARCHIVOS
    private void guardarResultadosEnArchivo(String resultado) {
        String nombreArchivo = "resultados" + numeroPartida + ".txt";
        try {
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write(resultado);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarPartida(int numeroPartida, String resultado) {
        String nombreArchivo = "resultados" + numeroPartida + ".txt";
        try {
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write(resultado);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


