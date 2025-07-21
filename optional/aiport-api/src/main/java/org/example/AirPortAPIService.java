package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirPortAPIService {

    @Value("${airport.api.url}")
    private String apiUrl;

    @Value("${airport.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public AirPortAPIService() {
        this.objectMapper = new ObjectMapper();
    }

    public String fetchApiResponse(String airportCode) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = apiUrl + "/" + airportCode;
            System.out.println("Making request to: " + url);

            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );

            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error fetching airport data: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    public List<AirportInfo> parseAirportResponse(String jsonResponse) {
        List<AirportInfo> airports = new ArrayList<>();

        try {
            if (jsonResponse.startsWith("Error:")) {
                System.err.println("API Error: " + jsonResponse);
                return airports;
            }

            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null) {
                if (dataNode.isObject()) {
                    AirportInfo airport = parseAirportNode(dataNode);
                    airports.add(airport);
                }
                else if (dataNode.isArray()) {
                    for (JsonNode airportNode : dataNode) {
                        AirportInfo airport = parseAirportNode(airportNode);
                        airports.add(airport);
                    }
                }
            }
            else if (rootNode.isObject() && rootNode.has("iata")) {
                AirportInfo airport = parseAirportNode(rootNode);
                airports.add(airport);
            }

        } catch (Exception e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            System.err.println("Response was: " + jsonResponse.substring(0, Math.min(200, jsonResponse.length())) + "...");
        }

        return airports;
    }


    private AirportInfo parseAirportNode(JsonNode airportNode) {
        AirportInfo airport = new AirportInfo();

        JsonNode attributesNode = airportNode.get("attributes");
        if (attributesNode == null) {
            attributesNode = airportNode;
        }

        airport.setIata(getStringValue(attributesNode, "iata"));
        airport.setIcao(getStringValue(attributesNode, "icao"));
        airport.setName(getStringValue(attributesNode, "name"));
        airport.setCity(getStringValue(attributesNode, "city"));
        airport.setRegion(getStringValue(attributesNode, "state"));
        if (airport.getRegion().equals("N/A")) {
            airport.setRegion(getStringValue(attributesNode, "region"));
        }
        airport.setCountry(getStringValue(attributesNode, "country"));

        airport.setElevationFt(getIntValue(attributesNode, "altitude"));
        if (airport.getElevationFt() == 0) {
            airport.setElevationFt(getIntValue(attributesNode, "elevation_ft"));
        }

        airport.setLatitude(getDoubleValue(attributesNode, "latitude"));
        airport.setLongitude(getDoubleValue(attributesNode, "longitude"));
        airport.setTimezone(getStringValue(attributesNode, "timezone"));

        return airport;
    }

    public List<AirportInfo> getAirportInfo(String airportCode) {
        String jsonResponse = fetchApiResponse(airportCode);
        return parseAirportResponse(jsonResponse);
    }

    public void printAirportInfo(List<AirportInfo> airports) {
        if (airports.isEmpty()) {
            System.out.println("No airport information found.");
            return;
        }

        for (AirportInfo airport : airports) {
            System.out.println("========================================");
            System.out.println("Airport: " + airport.getName());
            System.out.println("IATA Code: " + airport.getIata());
            System.out.println("ICAO Code: " + airport.getIcao());
            System.out.println("City: " + airport.getCity());
            System.out.println("Country: " + airport.getCountry());
            System.out.println("Region: " + airport.getRegion());
            System.out.println("Elevation: " + airport.getElevationFt() + " ft");
            System.out.println("Coordinates: " + airport.getLatitude() + ", " + airport.getLongitude());
            System.out.println("Timezone: " + airport.getTimezone());
            System.out.println("========================================");
        }
    }

    private String getStringValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : "N/A";
    }

    private int getIntValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asInt() : 0;
    }

    private double getDoubleValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asDouble() : 0.0;
    }
}
