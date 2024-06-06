package org.example.Clases;

import org.json.JSONArray;
import org.json.JSONObject;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class OpenF1Client {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
       // List<Driver> drivers = fetchDriversFromAPI();
       // createDriver(drivers);
       // System.out.println(drivers.toString());
        //List<Team> teams=generarGrilla();
        //System.out.println(teams);
        //List<Driver> drivers=generarListaPilotos(teams);
        //List<Circuit> circuits=generarListaCircuitos();
        //Campeonato campeonato = new Campeonato(teams, circuits);
        //String resultados = campeonato.simularCameponato();
       //System.out.println(resultados);
        //SwingUtilities.invokeLater(OpenF1GUI::new);
        F1Gui interfaz = new F1Gui();
        //createDriver(teams);
       //System.out.println(teams.toString());


    }

    public static List<Circuit> generarListaCircuitos(){
        List<Circuit> circuits= new ArrayList<>();
        try {
            URL url = new URL("https://api.openf1.org/v1/meetings"); // URL de la API de circuitos
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 indica éxito
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convertir la respuesta a un JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());

                // Procesar el JSONArray y construir objetos Circuit
                for (int i = 0; i < 23; i++) {
                    JSONObject circuitJson = jsonArray.getJSONObject(i);

                    Circuit circuit = new Circuit();
                    circuit.setLocacion(circuitJson.getString("location"));
                    circuit.setPais(circuitJson.getString("country_name"));
                    circuit.setNombre(circuitJson.getString("meeting_official_name"));


                    circuits.add(circuit);
                }
            } else {
                System.out.println("Error al obtener los datos, Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return circuits;
    }

    public static List<Driver> generarListaPilotos(List<Team> equipos){
        List<Driver> drivers=new ArrayList<>();
        for(Team equipo: equipos){
            drivers.add(equipo.getPrimer_piloto());
            drivers.add(equipo.getSegundo_piloto());
        }
        return drivers;
    }

    public static List<Team> generarGrilla(){
    List<Team> teams = new ArrayList<>();

    Driver piloto1 = new Driver("NED",1,"Max","https://www.formula1.com/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png.transform/1col/image.png","Verstappen","VER",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/MAXVER01.png"));
    Driver piloto2 = new Driver("MEX",11,"Sergio","https://www.formula1.com/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png.transform/1col/image.png","Perez","PER",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/SERPER01.png"));
    Driver piloto3 = new Driver("ESP",55,"Carlos","https://www.formula1.com/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png.transform/1col/image.png","Sainz","SAI",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/CARSAI01.png"));
    Driver piloto4 = new Driver("MON",16,"Charles","https://www.formula1.com/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png.transform/1col/image.png","Leclerc","LEC",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/CHALEC01.png"));
    Driver piloto5 = new Driver("ENG",44,"Lewis","https://www.formula1.com/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png.transform/1col/image.png","Hamilton","HAM",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/LEWHAM01.png"));
    Driver piloto6 = new Driver("ENG",63,"George","https://www.formula1.com/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png.transform/1col/image.png","Russell","RUS",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/GEORUS01.png"));
    Driver piloto7 = new Driver("ENG",4,"Lando","https://www.formula1.com/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png.transform/1col/image.png","Norris","NOR",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/LANNOR01.png"));
    Driver piloto8 = new Driver("AUS",81,"Oscar","https://www.formula1.com/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png.transform/1col/image.png","Piastri","PIA",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/OSCPIA01.png"));
    Driver piloto9 = new Driver("FRA",31,"Esteban","https://www.formula1.com/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png.transform/1col/image.png","Ocon","OCO",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/ESTOCO01.png"));
    Driver piloto10 = new Driver("FRA",10,"Pierre","https://www.formula1.com/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png.transform/1col/image.png","Gasly","GAS",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/PIEGAS01.png"));
    Driver piloto11 = new Driver("ESP",14,"Fernando","https://www.formula1.com/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png.transform/1col/image.png","Alonso","ALO",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/FERALO01.png"));
    Driver piloto12 = new Driver("CAN",18,"Lance","https://www.formula1.com/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png.transform/1col/image.png","Stroll","STR",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/LANSTR01.png"));
    Driver piloto13 = new Driver("AUS",3,"Daniel","https://www.formula1.com/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png.transform/1col/image.png","Ricciardo","RIC",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/DANRIC01.png"));
    Driver piloto14 = new Driver("JAP",22,"Yuki","https://www.formula1.com/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png.transform/1col/image.png","Tsunoda","TSU",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/YUKTSU01.png"));
    Driver piloto15 = new Driver("FIN",77,"Valtteri","https://www.formula1.com/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png.transform/1col/image.png","Bottas","BOT",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/VALBOT01.png"));
    Driver piloto16 = new Driver("CHI",24,"Guanyu","https://www.formula1.com/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png.transform/1col/image.png","Zhou","ZHO",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/GUAZHO01.png"));
    Driver piloto17 = new Driver("TAI",23,"Alex","https://www.formula1.com/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png.transform/1col/image.png","Albon","ALB",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/ALEALB01.png"));
    Driver piloto18 = new Driver("USA",2,"Logan","https://www.formula1.com/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png.transform/1col/image.png","Sargeant","SAR",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/LOGSAR01.png"));
    Driver piloto19 = new Driver("DEN",20,"Kevin","https://www.formula1.com/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png.transform/1col/image.png","Magnussen","MAG",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/KEVMAG01.png"));
    Driver piloto20 = new Driver("GER",27,"Nico","https://www.formula1.com/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png.transform/1col/image.png","Hulkenberg","HUL",new ImageIcon("src/main/java/org/example/imag/pngs/pngs/NICHUL01.png"));

    Team equipo1 = new Team("Red Bull Racing","#3671C6",piloto1,piloto2);
    teams.add(equipo1);
    Team equipo2 = new Team("Ferrari","#F91536",piloto4,piloto3);
    teams.add(equipo2);
    Team equipo3 = new Team("Mercedes","#6CD3BF",piloto5,piloto6);
    teams.add(equipo3);
    Team equipo4 = new Team("McLaren","#F58020",piloto7,piloto8);
    teams.add(equipo4);
    Team equipo7 = new Team("RB","#6692FF",piloto13,piloto14);
    teams.add(equipo7);
    Team equipo9 = new Team("Williams","#37BEDD",piloto17,piloto18);
    teams.add(equipo9);
    Team equipo8 = new Team("Kick Sauber","#52E252",piloto15,piloto16);
    teams.add(equipo8);
    Team equipo5 = new Team("Alpine","#2293D1",piloto9,piloto10);
    teams.add(equipo5);
    Team equipo6 = new Team("Aston Martin","#358C75",piloto11,piloto12);
    teams.add(equipo6);
    Team equipo10 = new Team("Haas","#B6BABD",piloto19,piloto20);
    teams.add(equipo10);

    return teams;
}

    private static List<Driver> fetchDriversFromAPI() {
        List<Driver> drivers = new ArrayList<>();

        try {
            URL url = new URL("https://api.openf1.org/v1/drivers"); // URL de la API de pilotos
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 indica éxito
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convertir la respuesta a un JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());

                // Procesar el JSONArray y construir objetos Driver
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject driverJson = jsonArray.getJSONObject(i);

                    Driver driver = new Driver();
                    //driver.setBroadcast_name(driverJson.getString("broadcast_name"));
                    driver.setCountry_code(driverJson.getString("country_code"));
                    driver.setDriver_number(driverJson.getInt("driver_number"));
                    driver.setFirst_name(driverJson.getString("first_name"));
                    driver.setFull_name(driverJson.getString("full_name"));
                    //driver.set(driverJson.getString("headshot_url"));
                    //driver.setLast_name(driverJson.getString("last_name"));
                    //driver.setMeeting_key(driverJson.getInt("meeting_key"));
                    driver.setName_acronym(driverJson.getString("name_acronym"));
                    //driver.setSession_key(driverJson.getInt("session_key"));
                    //driver.setTeam_colour(driverJson.getString("team_colour"));
                    //driver.setTeam_name(driverJson.getString("team_name"));

                    drivers.add(driver);
                }
            } else {
                System.out.println("Error al obtener los datos, Código de respuesta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return drivers;
    }

    public static void createDriver(List<Team> equipos) {
        System.out.println("Ingrese los datos del nuevo piloto:");

        // Solicitar y validar el Nombre
        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();

        // Solicitar y validar el Apellido
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();

        // Solicitar y validar el País
        System.out.print("País: ");
        String country = scanner.nextLine();
        country = country.substring(0, Math.min(country.length(), 3)).toUpperCase();

        // Solicitar y validar el Número del Auto
        int driverNumber;
        do {
            System.out.print("Número del Auto: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
            driverNumber = scanner.nextInt();
        } while (isDriverNumberUsed(driverNumber,equipos)); // Verificar si el número de auto ya está en uso
        // Construir el objeto Driver con los datos ingresados
        Driver newDriver = new Driver(lastName.substring(0, Math.min(lastName.length(), 3)).toUpperCase(), firstName,
                lastName,country,driverNumber);
        scanner.nextLine(); // Consumir el salto de línea pendiente
        int teamName;
        System.out.println("Para que escuderia quieres correr:" +
                "\n[0] Red Bull Racing" +
                "\n[1] Ferrari" +
                "\n[2] Mercedes" +
                "\n[3] McLaren" +
                "\n[4] Aston Martin" +
                "\n[5] RB" +
                "\n[6] Alpine" +
                "\n[7] Kick Sauber" +
                "\n[8] Williams" +
                "\n[9] Haas\n");
        teamName=scanner.nextInt();
        while (teamName<0 || teamName>10){
            System.out.println("\n Ingrese un numero valido"+
                            "\n[0] Red Bull Racing" +
                            "\n[1] Ferrari" +
                            "\n[2] Mercedes" +
                            "\n[3] McLaren" +
                            "\n[4] Aston Martin" +
                            "\n[5] RB" +
                            "\n[6] Alpine" +
                            "\n[7] Kick Sauber" +
                            "\n[8] Williams" +
                            "\n[9] Haas\n");
        teamName=scanner.nextInt();}

        int teamDriver;
        Team equipo;

        switch (teamName) {
            case 0:
                equipo=buscarEquipo(equipos,"Red Bull Racing");
                System.out.println("Quiere reemplazar a [1] Verstappen o [2] Perez");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Verstappen o [2] Perez: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 1:
                equipo=buscarEquipo(equipos,"Ferrari");
                System.out.println("Quiere reemplazar a [1] Leclerc o [2] Sainz");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Leclerc o [2] Sainz: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}

                break;
            case 2:
                equipo=buscarEquipo(equipos,"Mercedes");
                System.out.println("Quiere reemplazar a [1] Hamilton o [2] Russell");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Hamilton o [2] Russel: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}

                break;
            case 3:
                equipo=buscarEquipo(equipos,"McLaren");
                System.out.println("Quiere reemplazar a [1] Norris o [2] Piastri");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Norris o [2] Piastri: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 4:
                equipo=buscarEquipo(equipos,"Aston Martin");
                System.out.println("Quiere reemplazar a [1] Alonso o [2] Stroll");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Alonso o [2] Stroll: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 5:
                equipo=buscarEquipo(equipos,"RB");
                System.out.println("Quiere reemplazar a [1] Ricciardo o [2] Tsunoda");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Ricciardo o [2] Tsunoda: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 6:
                equipo=buscarEquipo(equipos,"Alpine");
                System.out.println("Quiere reemplazar a [1] Ocon o [2] Gasly");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Ocon o [2] Gasly: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}

                break;
            case 7:
                equipo=buscarEquipo(equipos,"Kick Sauber");
                System.out.println("Quiere reemplazar a [1] Bottas o [2] Zhou");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Bottas o [2] Zhou: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 8:
                equipo=buscarEquipo(equipos,"Williams");
                System.out.println("Quiere reemplazar a [1] Albon o [2] Sargeant");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Albon o [2] Sargeant: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            case 9:
                equipo=buscarEquipo(equipos,"Haas");
                System.out.println("Quiere reemplazar a [1] Magnussen o [2] Hulkenberg");
                teamDriver= scanner.nextInt();

                while(teamDriver != 1 && teamDriver != 2){
                    System.out.println("\n Ingrese un numero valido, [1] Magnussen o [2] Hulkenberg: ");
                    teamDriver= scanner.nextInt();}
                if(teamDriver==1){equipo.setPrimer_piloto(newDriver);
                }else {equipo.setSegundo_piloto(newDriver);}
                break;
            default:
                System.out.println("Número de equipo no válido");
                break;
        }




        // Imprimir los detalles del nuevo piloto creado
        System.out.println("\nNuevo piloto creado:");
        System.out.println(newDriver);
    }

    public static boolean isDriverNumberUsed(int driverNumber,List<Team> equipos) {
        boolean condicion=false;
        for (Team equipo : equipos) {
            if (equipo.getPrimer_piloto().getDriver_number() == driverNumber ||equipo.getSegundo_piloto().getDriver_number() == driverNumber ) {
                System.out.println("El número de auto ingresado ya está en uso.");
                condicion= true;
            }
        }
        return condicion;
    }



    public static Team buscarEquipo(List<Team> equipos, String nombreEquipo) {
        Team newEquipo=new Team();
        for (Team equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreEquipo)) {
                newEquipo= equipo; // Retorna el nombre del equipo encontrado
            }
        }
        return newEquipo; // Retorna un mensaje si el equipo no está en la lista
    }

}
