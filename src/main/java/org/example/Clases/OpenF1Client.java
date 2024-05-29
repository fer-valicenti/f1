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
        List<Team> teams=generarGrilla();
        //System.out.println(teams);
        List<Driver> drivers=generarListaPilotos();
        List<Circuit> circuits=generarListaCircuitos();
        Campeonato campeonato = new Campeonato(teams, circuits);
        String resultados = campeonato.simularCameponato();
       // System.out.println(resultados);
        //SwingUtilities.invokeLater(OpenF1GUI::new);
        F1Gui interfaz = new F1Gui();
        interfaz.iniciarJuego();

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
                for (int i = 0; i < jsonArray.length(); i++) {
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

    public static List<Driver> generarListaPilotos(){
        List<Driver> drivers=new ArrayList<>();
        Driver piloto1 = new Driver("NED",1,"Max","https://www.formula1.com/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png.transform/1col/image.png","Verstappen","VER");
        Driver piloto2 = new Driver("MEX",11,"Sergio","https://www.formula1.com/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png.transform/1col/image.png","Perez","PER");
        Driver piloto3 = new Driver("ESP",55,"Carlos","https://www.formula1.com/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png.transform/1col/image.png","Sainz","SAI");
        Driver piloto4 = new Driver("MON",16,"Charles","https://www.formula1.com/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png.transform/1col/image.png","Leclerc","LEC");
        Driver piloto5 = new Driver("ENG",44,"Lewis","https://www.formula1.com/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png.transform/1col/image.png","Hamilton","HAM");
        Driver piloto6 = new Driver("ENG",63,"George","https://www.formula1.com/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png.transform/1col/image.png","Russell","RUS");
        Driver piloto7 = new Driver("ENG",4,"Lando","https://www.formula1.com/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png.transform/1col/image.png","Norris","NOR");
        Driver piloto8 = new Driver("AUS",81,"Oscar","https://www.formula1.com/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png.transform/1col/image.png","Piastri","PIA");
        Driver piloto9 = new Driver("FRA",31,"Esteban","https://www.formula1.com/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png.transform/1col/image.png","Ocon","OCO");
        Driver piloto10 = new Driver("FRA",10,"Pierre","https://www.formula1.com/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png.transform/1col/image.png","Gasly","GAS");
        Driver piloto11 = new Driver("ESP",14,"Fernando","https://www.formula1.com/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png.transform/1col/image.png","Alonso","ALO");
        Driver piloto12 = new Driver("CAN",18,"Lance","https://www.formula1.com/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png.transform/1col/image.png","Stroll","STR");
        Driver piloto13 = new Driver("AUS",3,"Daniel","https://www.formula1.com/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png.transform/1col/image.png","Ricciardo","RIC");
        Driver piloto14 = new Driver("JAP",22,"Yuki","https://www.formula1.com/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png.transform/1col/image.png","Tsunoda","TSU");
        Driver piloto15 = new Driver("FIN",77,"Valtteri","https://www.formula1.com/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png.transform/1col/image.png","Bottas","BOT");
        Driver piloto16 = new Driver("CHI",24,"Guanyu","https://www.formula1.com/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png.transform/1col/image.png","Zhou","ZHO");
        Driver piloto17 = new Driver("TAI",23,"Alex","https://www.formula1.com/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png.transform/1col/image.png","Albon","ALB");
        Driver piloto18 = new Driver("USA",2,"Logan","https://www.formula1.com/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png.transform/1col/image.png","Sargeant","SAR");
        Driver piloto19 = new Driver("DEN",20,"Kevin","https://www.formula1.com/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png.transform/1col/image.png","Magnussen","MAG");
        Driver piloto20 = new Driver("GER",27,"Nico","https://www.formula1.com/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png.transform/1col/image.png","Hulkenberg","HUL");

        drivers.add(piloto1);
        drivers.add(piloto2);
        drivers.add(piloto3);
        drivers.add(piloto4);
        drivers.add(piloto5);
        drivers.add(piloto6);
        drivers.add(piloto7);
        drivers.add(piloto8);
        drivers.add(piloto9);
        drivers.add(piloto10);
        drivers.add(piloto12);
        drivers.add(piloto13);
        drivers.add(piloto14);
        drivers.add(piloto15);
        drivers.add(piloto16);
        drivers.add(piloto17);
        drivers.add(piloto18);
        drivers.add(piloto19);
        drivers.add(piloto20);
        return drivers;
    }

    public static List<Team> generarGrilla(){
    List<Team> teams = new ArrayList<>();

    Driver piloto1 = new Driver("NED",1,"Max","https://www.formula1.com/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png.transform/1col/image.png","Verstappen","VER");
    Driver piloto2 = new Driver("MEX",11,"Sergio","https://www.formula1.com/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png.transform/1col/image.png","Perez","PER");
    Driver piloto3 = new Driver("ESP",55,"Carlos","https://www.formula1.com/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png.transform/1col/image.png","Sainz","SAI");
    Driver piloto4 = new Driver("MON",16,"Charles","https://www.formula1.com/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png.transform/1col/image.png","Leclerc","LEC");
    Driver piloto5 = new Driver("ENG",44,"Lewis","https://www.formula1.com/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png.transform/1col/image.png","Hamilton","HAM");
    Driver piloto6 = new Driver("ENG",63,"George","https://www.formula1.com/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png.transform/1col/image.png","Russell","RUS");
    Driver piloto7 = new Driver("ENG",4,"Lando","https://www.formula1.com/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png.transform/1col/image.png","Norris","NOR");
    Driver piloto8 = new Driver("AUS",81,"Oscar","https://www.formula1.com/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png.transform/1col/image.png","Piastri","PIA");
    Driver piloto9 = new Driver("FRA",31,"Esteban","https://www.formula1.com/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png.transform/1col/image.png","Ocon","OCO");
    Driver piloto10 = new Driver("FRA",10,"Pierre","https://www.formula1.com/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png.transform/1col/image.png","Gasly","GAS");
    Driver piloto11 = new Driver("ESP",14,"Fernando","https://www.formula1.com/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png.transform/1col/image.png","Alonso","ALO");
    Driver piloto12 = new Driver("CAN",18,"Lance","https://www.formula1.com/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png.transform/1col/image.png","Stroll","STR");
    Driver piloto13 = new Driver("AUS",3,"Daniel","https://www.formula1.com/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png.transform/1col/image.png","Ricciardo","RIC");
    Driver piloto14 = new Driver("JAP",22,"Yuki","https://www.formula1.com/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png.transform/1col/image.png","Tsunoda","TSU");
    Driver piloto15 = new Driver("FIN",77,"Valtteri","https://www.formula1.com/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png.transform/1col/image.png","Bottas","BOT");
    Driver piloto16 = new Driver("CHI",24,"Guanyu","https://www.formula1.com/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png.transform/1col/image.png","Zhou","ZHO");
    Driver piloto17 = new Driver("TAI",23,"Alex","https://www.formula1.com/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png.transform/1col/image.png","Albon","ALB");
    Driver piloto18 = new Driver("USA",2,"Logan","https://www.formula1.com/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png.transform/1col/image.png","Sargeant","SAR");
    Driver piloto19 = new Driver("DEN",20,"Kevin","https://www.formula1.com/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png.transform/1col/image.png","Magnussen","MAG");
    Driver piloto20 = new Driver("GER",27,"Nico","https://www.formula1.com/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png.transform/1col/image.png","Hulkenberg","HUL");

    Team equipo1 = new Team("Red Bull Racing","#3671C6",piloto1,piloto2);
    teams.add(equipo1);
    Team equipo2 = new Team("Ferrari","#F91536",piloto3,piloto4);
    teams.add(equipo2);
    Team equipo3 = new Team("Mercedes","#6CD3BF",piloto5,piloto6);
    teams.add(equipo3);
    Team equipo4 = new Team("McLaren","#F58020",piloto7,piloto8);
    teams.add(equipo4);
    Team equipo5 = new Team("Alpine","#2293D1",piloto9,piloto10);
    teams.add(equipo5);
    Team equipo6 = new Team("Aston Martin","#358C75",piloto11,piloto12);
    teams.add(equipo6);
    Team equipo7 = new Team("RB","#6692FF",piloto13,piloto14);
    teams.add(equipo7);
    Team equipo8 = new Team("Kick Sauber","#52E252",piloto15,piloto16);
    teams.add(equipo8);
    Team equipo9 = new Team("Williams","#37BEDD",piloto17,piloto18);
    teams.add(equipo9);
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

    public static void createDriver(List<Driver> drivers) {
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
        } while (isDriverNumberUsed(driverNumber,drivers)); // Verificar si el número de auto ya está en uso

        scanner.nextLine(); // Consumir el salto de línea pendiente

        // Solicitar y validar el Equipo
/*
        String teamName;
        do {
            System.out.print("Equipo: ");
            teamName = scanner.nextLine();
        } while (!isTeamValid(teamName)); // Verificar si el equipo ingresado existe

        Obtener la lista de pilotos del equipo especificado
        List<String> teamDrivers = getTeamDrivers(teamName);

        // Preguntar al usuario a qué piloto quiere reemplazar
        String pilotToReplace;
        do {
            System.out.println("¿Qué piloto desea reemplazar?");
            for (String driver : teamDrivers) {
                System.out.println(driver);
            }
            System.out.print("Ingrese el apellido del piloto: ");
            pilotToReplace = scanner.nextLine();
        } while (!teamDrivers.contains(pilotToReplace)); // Verificar si el piloto ingresado está en la lista de pilotos del equipo
*/


        // Construir el objeto Driver con los datos ingresados
        Driver newDriver = new Driver(lastName.substring(0, Math.min(lastName.length(), 3)).toUpperCase(), firstName,
        lastName,country,driverNumber);

        drivers.add(newDriver);

        // Imprimir los detalles del nuevo piloto creado
        System.out.println("\nNuevo piloto creado:");
        System.out.println(newDriver);
    }

    public static boolean isDriverNumberUsed(int driverNumber,List<Driver> drivers) {
        for (Driver driver : drivers) {
            if (driver.getDriver_number() == driverNumber) {
                System.out.println("El número de auto ingresado ya está en uso.");
                return true;
            }
        }
        return false;
    }

    private static List<String> getTeamDrivers(String teamName) {
        // Aquí puedes implementar la lógica para obtener la lista de pilotos del equipo especificado
        // Por ahora, simplemente retornamos una lista de ejemplo para simular los pilotos del equipo
        switch (teamName.toLowerCase()) {
            case "red bull racing":
                return Arrays.asList("Verstappen", "Perez");
            case "mercedes":
                return Arrays.asList("Hamilton", "Russel");
            case "mclaren":
                return Arrays.asList("Norris", "Piastri");
            case "aston martin":
                return Arrays.asList("Alonso", "Stroll");
            case "alfa romeo":
                return Arrays.asList("Bottas", "Zhu");
            case "alpha tauri":
                return Arrays.asList("Ricciardo", "Tsunoda");
            case "ferrari":
                return Arrays.asList("Leclerc", "Sainz");
            case "haas":
                return Arrays.asList("Hulkenberg", "Magnussen");
            case "williams":
                return Arrays.asList("Albon", "Sergeant");
            case "alpine":
                return Arrays.asList("Gasly", "Ocon");
            default:
                return Collections.emptyList();
        }
    }

    private static boolean isTeamValid(String teamName) {
        // buscar si el equipo existe
        return true;
    }

}
