package com.prolandfarming.genericlogin.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Flight implements Serializable {
    public String flightUID;
    public String flightNumber;
    public String departureAirport;
    public String arrivalAirport;
    public String equipment;
    public Date departureDate;
    public Date arrivalDate;
    public String operatingAirline;
    public double distanceMiles;
    public List<String> chatroomsUID;

    public Flight(){

    }

}
