package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirportInfo {
    private String iata;
    private String icao;
    private String name;
    private String city;
    private String region;
    private String country;
    private int elevationFt;
    private double latitude;
    private double longitude;
    private String timezone;
}
