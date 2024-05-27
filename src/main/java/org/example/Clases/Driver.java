package org.example.Clases;

import org.json.JSONObject;


import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Driver {

    private String country_code;
    private int driver_number;
    private String first_name;
    private String full_name;
    private URL headshot_url;
    private String last_name;
    private String name_acronym;

    public Driver(){
        country_code="0";
        driver_number=0;
        first_name="";
        full_name="";
        last_name="";
        name_acronym="";
    }

    public Driver(String country_code, int driver_number, String first_name,String headshot_url, String last_name, String name_acronym) {
        this.country_code = country_code;
        this.driver_number = driver_number;
        this.first_name = first_name;
        this.full_name = first_name + " " + last_name;
        URL url;
        try {
             url=new URL(headshot_url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.headshot_url = url;
        this.last_name = last_name;
        this.name_acronym = name_acronym;

    }
    public Driver(JSONObject json)
    {
        //this.broadcast_name = json.getString("broadcast_name");
        this.country_code = json.getString("country_code");
        this.driver_number = json.getInt("driver_number");
        this.first_name = json.getString("first_name");
        this.full_name = json.getString("full_name");
        //this.headshot_url = json.getJSONObject("headshot_url");
        this.last_name = json.getString("last_name");
        this.name_acronym = json.getString("name_acronym");
    }

    public Driver(String upperCase,String firstName, String lastName, String country, int driverNumber) {
        country_code=country;
        driver_number=driverNumber;
        first_name=firstName;
        last_name=lastName;
        name_acronym=upperCase;

    }


    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getDriver_number() {
        return driver_number;
    }

    public void setDriver_number(int driver_number) {
        this.driver_number = driver_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

   public URL getHeadshot_url() {
        return headshot_url;
    }

    public void setHeadshot_url(URL headshot_url) {
        this.headshot_url = headshot_url;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName_acronym() {
        return name_acronym;
    }

    public void setName_acronym(String name_acronym) {
        this.name_acronym = name_acronym;
    }



    @Override
    public String toString() {
        return full_name;
    }
}
