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

    public static void main(String[] args){
        F1Gui interfaz = new F1Gui();
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

}
