package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private AirPortAPIService airPortAPIService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Testing Airport API Service...");
        System.out.println("=".repeat(50));

        // Test fetching raw JSON response
        System.out.println("1. Fetching raw JSON response for LAX airport:");
        String rawResponse = airPortAPIService.fetchApiResponse("LAX");
        System.out.println("Raw JSON Response:");
        System.out.println(rawResponse);
        System.out.println();

        // Test parsing and displaying formatted airport information
        System.out.println("2. Fetching and parsing airport information:");
        List<AirportInfo> airports = airPortAPIService.getAirportInfo("LAX");
        airPortAPIService.printAirportInfo(airports);

        // Test with another airport
        System.out.println("3. Testing with JFK airport:");
        List<AirportInfo> jfkAirports = airPortAPIService.getAirportInfo("JFK");
        airPortAPIService.printAirportInfo(jfkAirports);
    }
}