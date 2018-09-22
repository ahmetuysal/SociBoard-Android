package com.prolandfarming.genericlogin;

import com.prolandfarming.genericlogin.Services.THYService;

import java.io.IOException;

public class ApiTest {

    public static void main(String[] args) {



        System.out.println("Api Testing...\n");

        try {
            THYService thyService = new THYService("AHK34D");
            System.out.println("Arrival Date: "+thyService.getArrivalDate());
            System.out.println("Arrival Location: "+thyService.getArrivalLocation());
            System.out.println("Departure Date: "+thyService.getDepartureDate());
            System.out.println("Departure Location: "+thyService.getDepartureLocation());
            System.out.println("Flight Code: "+thyService.getFlightCode());
            System.out.println("IsCheckedIn: "+thyService.isCheckedin());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
