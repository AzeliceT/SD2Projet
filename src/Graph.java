import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Graph {

  protected Map<String, City> correspondanceIataCity;
  protected Map<Integer, City> correspondanceIdCity;
  protected List<Road> roadsList;

  public Graph(File cities, File roads) {
    correspondanceIataCity = new HashMap<>();
    roadsList = new ArrayList<>();
    correspondanceIdCity = new HashMap<>();
    readCities(cities);
    readRoads(roads);
  }

  private void readCities(File cities) {
    try (Scanner cityScanner = new Scanner(cities)) {
      while (cityScanner.hasNextLine()) {

        String cityData = cityScanner.nextLine();
        String[] cityDataArray = cityData.split(",");

        City city = new City(Integer.parseInt(cityDataArray[0]), cityDataArray[1],Double.parseDouble(cityDataArray[2]),  Double.parseDouble(cityDataArray[3]));
        correspondanceIataCity.put(cityDataArray[0], city);

      }
    } catch (RuntimeException | FileNotFoundException e) {
      System.out.println("A runtime exception occurred: " + e.getMessage());
    }
  }

  private void readRoads(File roads) {
    try (Scanner roadScanner = new Scanner(roads)) {
      while (roadScanner.hasNextLine()) {

        String roadData = roadScanner.nextLine();
        String[] roadDataArray = roadData.split(",");

        Road road = new Road(Integer.parseInt(roadDataArray[0]), Integer.parseInt(roadDataArray[1]));
        roadsList.add(road);

      }
    } catch (RuntimeException | FileNotFoundException e) {
      System.out.println("A runtime exception occurred: " + e.getMessage());
    }
  }

  public double calculerItineraireMinimisantNombreRoutes(String cityDeparture, String cityArrival) {


    return 0;
  }

  public double calculerItineraireMinimisantKm(String cityDeparture, String cityArrival) {
    // TODO Auto-generated method stub
    return 0;
  }
}
