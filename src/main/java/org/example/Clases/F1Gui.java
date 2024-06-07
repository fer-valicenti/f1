package org.example.Clases;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.*;


//https://youtu.be/mGkrbzJZCoE

public class F1Gui extends JFrame {

    private JComboBox<Driver> driverComboBox;
    private JComboBox<Team> teamComboBox;
    private JButton startButton, restartButton, selectDriverButton, createDriverButton, createCircuitButton, selectChangeDriverButton, selectChangeDriverButton2, iniciarCampeonatoButton, siguienteButton, correrGranPrixButton, createDriversButtton, podioButton;
    private List<Team> teams;
    private List<Driver> drivers;
    private List<Circuit> circuits;
    private Campeonato campeonato;
    private JLabel backgroundLabel;
    private Driver selectedDriver;
    private Team selectedTeam;
    private Clip clip;
    private JTextArea resultadosTextArea, textArea;
    private JScrollPane scrollPane;

    public static int circuitoActual = 0; //Declarar circuitoActual como un campo de clase

    public static int numeroPartida = 1;

    public F1Gui() {
        setTitle("FI GAME");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        circuits = OpenF1Client.generarListaCircuitos();
        teams = OpenF1Client.generarGrilla();
        drivers = OpenF1Client.generarListaPilotos(teams);

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

    /**
     *
     *Este método se llama cuando se hace clic en el botón "Iniciar Juego". Realiza las siguientes acciones:
     * Oculta el botón de "Iniciar Juego".
     * Muestra botones para seleccionar un piloto, crear un piloto, ver la lista de circuitos, ver la lista de pilotos y salir del juego.
     */

    public void iniciarJuego() {
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

        // Boton lista de pilotos
        createDriversButtton = new JButton("Lista de Pilotos");
        createDriversButtton.setBounds(320, 500, 150, 30);
        createDriversButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTeamsDialog(teams);
            }
        });
        add(createDriversButtton);

        restartButton = new JButton("Salir");
        restartButton.setBounds(320, 540, 150, 30);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        add(restartButton);

        setVisible(true);
    }

    /**
     *
     *Este método se llama cuando se hace clic en el botón "Seleccionar Piloto". Realiza las siguientes acciones:
     * Muestra un diálogo para seleccionar un piloto de una lista desplegable de pilotos disponibles.
     * Cuando se selecciona un piloto, se oculta el diálogo y se muestra el botón "Iniciar Campeonato" y el botón "Correr Grand Prix".
     * También se ocultan otros botones relacionados con la selección de pilotos.
     */

    private void seleccionarPiloto() {
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
                createDriversButtton.setVisible(false);
            }
        });
        selectDriverDialog.add(selectButton);

        selectDriverDialog.setVisible(true);
    }

    /**
     *Este método muestra el botón "Iniciar Campeonato" y el botón "Correr Grand Prix" después de seleccionar un piloto.
     */
    private void mostrarBotonIniciarCampeonato() {
        if (selectedDriver != null) {
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

    /**
     *
     Este método se llama cuando se hace clic en el botón "Crear Piloto". Realiza las siguientes acciones:
     Muestra un diálogo para que el usuario ingrese los detalles de un nuevo piloto, como nombre, apellido, país y número de auto.
     Una vez que se ingresan los detalles y se hace clic en "Crear", se crea un nuevo objeto Driver con los detalles proporcionados.
     Se muestra un mensaje de confirmación con los detalles del nuevo piloto creado.
     Luego se abre un diálogo para seleccionar el equipo al que se desea asignar el nuevo piloto.
     *
     */

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
                drivers = OpenF1Client.generarListaPilotos(teams);
                // selectCircuitButton.setEnabled(true); // Habilitar selección de circuito después de crear un piloto
            }
            //seleccionar equipo
        });
        createDriverDialog.add(createButton);

        createDriverDialog.setVisible(true);
    }

    /**
     *
     *Este método se llama cuando se hace clic en el botón "¡Iniciar Campeonato!". Realiza las siguientes acciones:
     * Oculta el botón "¡Iniciar Campeonato!".
     * Crea un nuevo objeto Campeonato con la lista de equipos y circuitos generados anteriormente.
     * Muestra un área de texto para mostrar los resultados de las carreras.
     * Muestra el botón "Siguiente Carrera" para simular la primera carrera.
     */

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
        if (correrGranPrixButton != null) {
            correrGranPrixButton.setVisible(false);
        }

        mostrarResultadoCircuitoActual();

    }

    /**
     *
     *Este método simula una carrera en el circuito actual y muestra los resultados en el área de texto. Realiza las siguientes acciones:
     * Si aún quedan circuitos por correr, muestra una animación de carga y simula la carrera.
     * Una vez completada la simulación, muestra los resultados de la carrera en el área de texto.
     * Avanza al siguiente circuito si aún quedan más por correr.
     */

    private void mostrarResultadoCircuitoActual() {
        if (circuitoActual < circuits.size()) {
            Circuit circuito = circuits.get(circuitoActual);

            // Crear un label para mostrar el GIF
            ImageIcon loadingIcon = new ImageIcon("src/main/java/org/example/imag/gif.gif");
            JLabel loadingLabel = new JLabel(loadingIcon);
            loadingLabel.setBounds(resultadosTextArea.getBounds()); // Usar las mismas dimensiones que resultadosTextArea
            loadingLabel.setOpaque(true); // Asegurar que el label sea opaco
            add(loadingLabel, Integer.valueOf(1)); // Agregar el label con un alto índice de Z para que quede al frente
            // Forzar la actualización de la interfaz de usuario
            revalidate();
            repaint();

            // Usar SwingWorker para manejar el retraso y la simulación de la carrera
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Simular retraso para mostrar la animación
                    Thread.sleep(1500); // Puedes ajustar la duración según sea necesario

                    return null;
                }

                @Override
                protected void done() {
                    // Quitar la animación GIF
                    remove(loadingLabel);
                    revalidate();
                    repaint();

                    String nombreCircuito = circuito.getNombre();
                    Map<Driver,Integer> mapa = campeonato.simularCarrera(circuito);
                    String tablaCarrera = campeonato.getTablaCarrera();
                    String resultadoCircuito = nombreCircuito + "\n\n"+ "Resultado de la Carrera"+"\n\n"+ tablaCarrera +"\n\n"+"Campeonato de Pilotos"+"\n\n" + campeonato.generarTablaPosicionesCarrera(mapa);

                    resultadosTextArea.setText(resultadoCircuito);
                    resultadosTextArea.setCaretPosition(0);

                    // Para que la barra comience desde arriba
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            scrollPane.getViewport().setViewPosition(new Point(0, 0));
                        }
                    });

                    circuitoActual++;

                    if (circuitoActual >= circuits.size()) {
                        siguienteButton.setText("Ver Resultado Final");
                        siguienteButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String tabla=campeonato.generarTablaFinal(mapa);
                                System.out.println(tabla);
                                mostrarResultadoFinal(tabla);
                                siguienteButton.setEnabled(false);
                            }
                        });

                    }
                    guardarResultadosEnArchivo(resultadoCircuito);
                }
            };

            // Iniciar el SwingWorker
            worker.execute();
        }
    }

    /**
     *
     Este método muestra los resultados finales del campeonato y ofrece la opción de ver el podio. Realiza las siguientes acciones:
     Muestra los resultados finales del campeonato en el área de texto.
     Guarda los resultados en un archivo.
     Muestra un botón para ver el podio.
     */

    private void mostrarResultadoFinal(String resultadoFinal) {
/*
        siguienteButton.setVisible(false);
        String resultadoFinal = campeonato.simularCameponato();
        resultadosTextArea.setText(resultadoFinal);
        resultadosTextArea.setCaretPosition(0);
        guardarPartida(numeroPartida, resultadoFinal);
        numeroPartida++;


        podioButton = new JButton("Ver podio");
        podioButton.setBounds(320, 500, 150, 30);
        podioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Driver> lista = generarPodio(resultadoFinal);
                mostrarPodio(lista);

            }
        });
        add(podioButton);



 */

        siguienteButton.setVisible(false);
        resultadosTextArea.setText(resultadoFinal);
        resultadosTextArea.setCaretPosition(0);
        guardarPartida(numeroPartida, resultadoFinal);
        numeroPartida++;


        podioButton = new JButton("Ver podio");
        podioButton.setBounds(320, 500, 150, 30);
        podioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Driver> lista = generarPodio(resultadoFinal);
                mostrarPodio(lista);

            }
        });
        add(podioButton);

    }

    /**
     *
     Este método muestra una lista de nombres de circuitos en un cuadro de diálogo
     */

    private void listarCircuitos() {
        StringBuilder circuitosText = new StringBuilder();
        for (Circuit circuit : circuits) {
            circuitosText.append(circuit.getNombre()).append("\n");
        }
        JOptionPane.showMessageDialog(this, circuitosText.toString(), "Lista de Circuitos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     *Este método simula un Gran Prix en un circuito seleccionado. Realiza las siguientes acciones:
     * Muestra un cuadro de diálogo para seleccionar el circuito en el que se desea correr.
     * Simula la carrera en el circuito seleccionado.
     * Muestra los resultados en un cuadro de diálogo.
     * Guarda los resultados en un archivo.
     * Actualiza el podio según los resultados de la carrera.
     */

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
                List<Circuit> circuito = new ArrayList<>();

                circuito.add(selectedCircuit);
                campeonato = new Campeonato(teams, circuito);
                String nombreCircuito = selectedCircuit.getNombre();
                String resultadoCircuito = nombreCircuito + "\n\n" + campeonato.generarTablaPosicionesCarrera(campeonato.simularCarrera(selectedCircuit));
                mostrarTablaResultado(resultadoCircuito, "Resultado del Gran Prix");
                guardarPartida(numeroPartida, resultadoCircuito);
                numeroPartida++;
                actualizarPodio(resultadoCircuito);
/*
                podioButton = new JButton("Ver podio");
                podioButton.setBounds(320, 500, 150, 30);
                podioButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actualizarPodio(resultadoCircuito);

                    }
                });
                add(podioButton);

 */
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
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(parentFrame); // Aparece en el centro del JFrame principal
        dialog.setVisible(true);
    }

    /**
     *
     *Este método reinicia el juego, restableciendo las variables y configuraciones a su estado inicial.
     */

    private void reiniciarJuego() {
        circuitoActual = 0;
        numeroPartida++;
        selectedDriver = null;
        campeonato = null;

        getContentPane().removeAll();
        repaint();

        setTitle("FI GAME");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        circuits = OpenF1Client.generarListaCircuitos();
        teams = OpenF1Client.generarGrilla();
        drivers = OpenF1Client.generarListaPilotos(teams);

        ImageIcon backgroundIcon = new ImageIcon("src/main/java/org/example/imag/f1-24-game.jpg");
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setSize(800, 600);
        setContentPane(backgroundLabel);

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

    private boolean isDriverNumberUsed(int number) {
        // Devuelve true si el número de auto ya está asignado a otro piloto, de lo contrario, devuelve false
        for (Driver driver : drivers) { // Suponiendo que drivers es la lista de todos los pilotos
            if (driver.getDriver_number() == number) {
                return true; // El número de auto está en uso
            }
        }
        return false; // El número de auto no está en uso
    }

    private void PlayMusic(String filePath) {

        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *Este método se llama cuando se crea un nuevo piloto y se debe seleccionar el equipo al que pertenecerá. Realiza las siguientes acciones:
     * Muestra un cuadro de diálogo con una lista desplegable de equipos disponibles.
     * Cuando se selecciona un equipo y se confirma, el piloto recién creado se asigna a ese equipo.
     * Si todos los equipos están llenos, se muestra un mensaje de error y se solicita al usuario que seleccione otro equipo o cancele la operació
     */

    private void seleccionarEquipo(Driver nuevoPiloto) {
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
                reemplazarPiloto(selectedTeam, nuevoPiloto);

            }
        });
        selectTeamDialog.add(selectButton);

        selectTeamDialog.setVisible(true);
    }

    /**
     *
     *Este método se llama cuando se selecciona un piloto existente y se desea cambiar el equipo al que pertenece. Realiza las siguientes acciones:
     * Muestra un cuadro de diálogo para seleccionar el nuevo equipo para el piloto.
     * Cuando se selecciona un nuevo equipo y se confirma, el piloto se reemplaza en su equipo actual por otro piloto y se asigna al nuevo equipo
     */
    private void reemplazarPiloto(Team equipo, Driver nuevoPiloto) {
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
                nuevoPiloto.setNumberImage(equipo.getPrimer_piloto().getNumberImage());
                nuevoPiloto.setHeadshot_url(equipo.getPrimer_piloto().getHeadshot_url());
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
                nuevoPiloto.setNumberImage(equipo.getSegundo_piloto().getNumberImage());
                nuevoPiloto.setHeadshot_url(equipo.getSegundo_piloto().getHeadshot_url());
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

    /**
     *
     *Este método muestra una ventana emergente que presenta información sobre los equipos de Fórmula 1,
     *  incluyendo los nombres de los pilotos de cada equipo y la paleta de colores del equipo.

     */

    private void showTeamsDialog(List<Team> teams) {


        JDialog dialog = new JDialog();
        dialog.setTitle("Fórmula 1 Teams");
        dialog.setSize(1280, 720);
        dialog.setLayout(new BorderLayout());

        // Crear un JLabel para la imagen de fondo
        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/java/org/example/imag/BR2HTOLUJBBODM55RWTRNX5JSQ.jpg"));
        backgroundLabel.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridLayout(5, 1));
        leftPanel.setOpaque(false); // Hacer el panel transparente
        JPanel rightPanel = new JPanel(new GridLayout(5, 1));
        rightPanel.setOpaque(false); // Hacer el panel transparente

        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            JPanel teamPanel = new JPanel(new BorderLayout());
            teamPanel.setOpaque(false); // Hacer el panel transparente

            // Crear la barra de color del equipo
            JPanel colorBar = new JPanel();
            colorBar.setBackground(team.getTeam_color());
            colorBar.setPreferredSize(new Dimension(25, 50));

            // Crear un panel para los pilotos
            JPanel driversPanel = new JPanel(new GridLayout(2, 1));
            driversPanel.setOpaque(false); // Hacer el panel transparente

            // Añadir el primer piloto
            JPanel driver1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            driver1Panel.setOpaque(false);

            JLabel driver1Label = new JLabel(team.getPrimer_piloto().getName_acronym());
            driver1Label.setIcon(new ImageIcon(team.getPrimer_piloto().getHeadshot_url()));
            driver1Panel.add(driver1Label);

            // Redimensionar la imagen del número
            ImageIcon number1Icon = new ImageIcon(String.valueOf(team.getPrimer_piloto().getNumberImage()));
            Image number1Image = number1Icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel number1Label = new JLabel(new ImageIcon(number1Image));
            driver1Panel.add(number1Label);

            driversPanel.add(driver1Panel);

            // Añadir el segundo piloto
            JPanel driver2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            driver2Panel.setOpaque(false);

            JLabel driver2Label = new JLabel(team.getSegundo_piloto().getName_acronym());
            driver2Label.setIcon(new ImageIcon(team.getSegundo_piloto().getHeadshot_url()));
            driver2Panel.add(driver2Label);

            // Redimensionar la imagen del número
            ImageIcon number2Icon = new ImageIcon(String.valueOf(team.getSegundo_piloto().getNumberImage()));
            Image number2Image = number2Icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel number2Label = new JLabel(new ImageIcon(number2Image));
            driver2Panel.add(number2Label);

            driversPanel.add(driver2Panel);

            // Añadir los componentes al panel del equipo
            teamPanel.add(colorBar, BorderLayout.WEST);
            teamPanel.add(driversPanel, BorderLayout.CENTER);

            if (i < 5) {
                leftPanel.add(teamPanel);
            } else {
                rightPanel.add(teamPanel);
            }
        }

        backgroundLabel.add(leftPanel);
        backgroundLabel.add(rightPanel);

        dialog.add(backgroundLabel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    /**
     *
     * Este método genera y muestra el podio basado en los resultados finales del campeonato. Realiza las siguientes acciones:
     * Obtiene los resultados finales del campeonato.
     * Calcula los tres primeros pilotos basándose en los puntos acumulados.
     * Muestra una ventana emergente con los nombres y las posiciones de los tres primeros pilotos en el podio.
     *
     */
    public List<Driver> generarPodio(String tabla) {

        List<Driver> topThree = new ArrayList<>();
        String[] lines = tabla.split("\n");
        int count = 0;

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("Tabla")) {
                continue;
            }

            String[] parts = line.split(" - ");
            if (parts.length > 1) {
                String driver = parts[0].substring(parts[0].indexOf(".") + 2);
                for (Team equipo : teams) {
                    if (driver.equalsIgnoreCase(equipo.getPrimer_piloto().getFull_name())) {
                        topThree.add(equipo.getPrimer_piloto());
                    }
                    if (driver.equalsIgnoreCase(equipo.getSegundo_piloto().getFull_name())) {
                        topThree.add(equipo.getSegundo_piloto());
                    }
                }
                count++;
            }

            if (count == 3) {
                break;
            }
        }

        return topThree;
    }

    public void mostrarPodio(List<Driver> drivers) {

        JDialog dialog = new JDialog();
        dialog.setTitle("Podio");
        dialog.setSize(626, 626);
        dialog.setLayout(new BorderLayout());

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/main/java/org/example/imag/ganador-deportes-vacio-podio-iluminado-reflectores-escenario-vacio-reflector-iluminado-podio-ganador-pedestal_53562-5276.jpg"));
        backgroundLabel.setLayout(null); // No layout manager for absolute positioning

        // Create a panel for the podium
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, dialog.getWidth(), dialog.getHeight());

        // Add the background label to the layered pane
        backgroundLabel.setBounds(0, 0, dialog.getWidth(), dialog.getHeight());
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Add drivers' headshots to the layered pane
        int[][] positions = {
                {260, 210}, // Coordenadas para el primer piloto
                 {380, 250},  // Coordenadas para el tercer piloto (ajustado 20 px hacia arriba)
                {130, 280}, // Coordenadas para el segundo piloto (ajustado 20 px hacia arriba)

        };

        for (int i = 0; i < drivers.size(); i++) {
            Driver piloto = drivers.get(i);
            ImageIcon headshotIcon = new ImageIcon(piloto.getHeadshot_url());
            JLabel headshotLabel = new JLabel(headshotIcon);

            headshotLabel.setBounds(positions[i][0], positions[i][1], headshotIcon.getIconWidth(), headshotIcon.getIconHeight());
            layeredPane.add(headshotLabel, Integer.valueOf(1));
        }

        // Add the layered pane to the dialog
        dialog.add(layeredPane);

        // Set dialog properties
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);
}
    private void actualizarPodio(String resultadoCircuito) {
        List<Driver> listaPodio = generarPodio(resultadoCircuito);
        mostrarPodio(listaPodio);
    }
}








