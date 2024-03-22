import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

        City city = new City(Integer.parseInt(cityDataArray[0]), cityDataArray[1],
            Double.parseDouble(cityDataArray[2]), Double.parseDouble(cityDataArray[3]));
        correspondanceIataCity.put(cityDataArray[1], city);
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

        Road road = new Road(Integer.parseInt(roadDataArray[0]),
            Integer.parseInt(roadDataArray[1]));
        roadsList.add(road);


      }
    } catch (RuntimeException | FileNotFoundException e) {
      System.out.println("A runtime exception occurred: " + e.getMessage());
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String cityDeparture, String cityArrival) {
    if (!correspondanceIataCity.containsKey(cityDeparture) || !correspondanceIataCity.containsKey(
        cityArrival)) {
      throw new IllegalArgumentException("City not found");
    }

    Queue<City> citiesVisited = new LinkedList<>();

    HashSet<City> citiesVisitedSet = new HashSet<>();

    HashMap<City, Road> cityRoads = new HashMap<>();

    City startCity = correspondanceIataCity.get(cityDeparture);
    citiesVisited.add(startCity);
    citiesVisitedSet.add(startCity);

    while (!citiesVisited.isEmpty()) {
      City currentCity = citiesVisited.poll();
      System.out.println(citiesVisited.size());

      for (Road road : roadsList) {
        if (road.getIdSource() == currentCity.getId()) {
          // 137 = 137
          City destinationCity = correspondanceIdCity.get(road.getIdDestination());
          if (!citiesVisitedSet.contains(destinationCity)) {
            citiesVisited.add(destinationCity);
            citiesVisitedSet.add(destinationCity);
            cityRoads.put(destinationCity, road);
          }

          if (currentCity.getName().equals(cityArrival)) {
            System.out.println(cityDeparture);
            System.out.println(cityArrival);
            System.out.println(
                "La route courante est égale à la route d'arrivée, arrivé à destination : "
                    + destinationCity.getName());

            ArrayList<Road> roads = new ArrayList<>();

            while (cityRoads.get(destinationCity) != null) {
              roads.add(cityRoads.get(destinationCity));
              destinationCity = correspondanceIdCity.get(cityRoads.get(destinationCity).getIdSource());
            }
            // faut boucler sur roads => ca va afficher la route inverse et faut faire un truc pour le mettre back wards
            for (int i = roads.size() - 1; i >= 0; i--) {
              System.out.println(correspondanceIdCity.get(roads.get(i).getIdSource()).getName() + " -> " + correspondanceIdCity.get(roads.get(i).getIdDestination()).getName());
            }
            return;
          }
        }

      }
    }
    System.out.println("No route found between the two cities.");

//    public double calculerItineraireMinimisantKm (String cityDeparture, String cityArrival){
//      // TODO Auto-generated method stub
//      return 0;
//    }
  }


}
