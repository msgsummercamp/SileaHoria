package org.example;

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

    public AirportInfo() {}

    public AirportInfo(String iata, String icao, String name, String city, String region,
                      String country, int elevationFt, double latitude, double longitude, String timezone) {
        this.iata = iata;
        this.icao = icao;
        this.name = name;
        this.city = city;
        this.region = region;
        this.country = country;
        this.elevationFt = elevationFt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getElevationFt() {
        return elevationFt;
    }

    public void setElevationFt(int elevationFt) {
        this.elevationFt = elevationFt;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "AirportInfo{" +
                "iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", elevationFt=" + elevationFt +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}
