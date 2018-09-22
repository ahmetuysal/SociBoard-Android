package com.prolandfarming.genericlogin.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class THYService {

    public String arrivalDate;
    public String arrivalLocation;
    public String arrivalTime;

    public String departureDate;
    public String departureLocation;
    public String getDepartureTime;

    public String flightCode;
    public boolean checkedin;

    public THYService(String pnrNumber) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://api.turkishairlines.com/test/searchpassenger");
        urlBuilder.append("?");
        // InternalStorage.readObject(this, "user_credentials.data");
        urlBuilder.append(URLEncoder.encode("lastname","UTF-8") + "=" + URLEncoder.encode("YILMAZ", "UTF-8") + "&");
        urlBuilder.append(URLEncoder.encode("pnr","UTF-8") + "=" + URLEncoder.encode(pnrNumber, "UTF-8") + "&");
        urlBuilder.append(URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode("AHMET", "UTF-8") + "&");
        urlBuilder.append(URLEncoder.encode("title","UTF-8") + "=" + URLEncoder.encode("MRS", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("apisecret", "885c340e96ac4c7a9638c021ccbe8a01");
        conn.setRequestProperty("apikey", "l7xxf90f2f436d3b48bba2a0d0ef5aec7008");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        //System.out.println(sb.toString());
        String arrivalInfo = sb.toString();
        String[] arrivalDate = arrivalInfo.split("\"ArrivalDate\": \"");
        String arrivalD = arrivalDate[1].split("\"")[0];
        //System.out.println(arrivalD);
        this.arrivalDate = arrivalD;

        String arrivalLocation = arrivalInfo.split("\"LocationCode\": \"")[1].split("\"")[0];
        this.arrivalLocation = arrivalLocation;

        String arrivalTime = arrivalInfo.split("\"ArrivalTime\": \"")[1].split("\"")[0];
        this.arrivalTime = arrivalTime;

        String departureDate = arrivalInfo.split("\"DepartureDate\": \"")[1].split("\"")[0];
        this.departureDate = departureDate;


        String departureLocation = arrivalInfo.split("\"LocationCode\": \"")[2].split("\"")[0];
        this.departureLocation = departureLocation;

        String flightCode = arrivalInfo.split("\"code\" : \"")[1].split("\"")[0];
        this.flightCode = flightCode;

        String isCheckedIn = arrivalInfo.split("\"CheckinStateInfo\":")[1].split("\"State\": \"")[1].split("\"")[0];
        this.checkedin = isCheckedIn.equals("NOT_CHECKEDIN") ? false : true;


    }

    public String getArrivalDate(){
        return this.arrivalDate;
    }

    public String getArrivalLocation(){
        return this.arrivalLocation;
    }

    public String getArrivalTime(){
        return this.arrivalTime;
    }

    public String getDepartureDate(){
        return this.departureDate;
    }

    public String getDepartureLocation(){
        return this.departureLocation;
    }

    public String getFlightCode(){
        return this.flightCode;
    }

    public boolean isCheckedin(){
        return this.checkedin;
    }


}
