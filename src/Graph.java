import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;


public class Graph {
  private Map<Integer, City> citiesMap;
  private Map<String, City> citiesMapNames;
  private List<Road> roadsList;

  public Graph(File citiesFile, File roadsFile) {
    citiesMap = new HashMap<>();
    citiesMapNames = new HashMap<>();
    roadsList = new ArrayList<>();

    try {
      Scanner cityScanner = new Scanner(citiesFile);
      while (cityScanner.hasNextLine()) {
        String[] cityData = cityScanner.nextLine().split(",");
        int id = Integer.parseInt(cityData[0]);
        String cityName = cityData[1];
        double longitude = Double.parseDouble(cityData[2]);
        double latitude = Double.parseDouble(cityData[3]);
        citiesMap.put(id, new City(id, cityName, latitude, longitude));
        citiesMapNames.put(cityName, new City(id, cityName, latitude, longitude));
      }
      cityScanner.close();

      Scanner roadScanner = new Scanner(roadsFile);
      while (roadScanner.hasNextLine()) {
        String[] roadData = roadScanner.nextLine().split(",");
        int city1Id = Integer.parseInt(roadData[0]);
        int city2Id = Integer.parseInt(roadData[1]);
        roadsList.add(new Road(city1Id, city2Id));
      }
      roadScanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String startCity, String endCity) {

    Queue<Integer> citiesToVisit = new LinkedList<>();
    Map<Integer, Integer> parentMap = new HashMap<>();
    Set<Integer> MarkedCities = new HashSet<>();


    citiesToVisit.add(citiesMapNames.get(startCity).getId());

    while (!citiesToVisit.isEmpty()) {
      int currentCityId = citiesToVisit.poll();
      if (currentCityId == citiesMapNames.get(endCity).getId()) {
        break;
      }
      for (Road road : roadsList) {
        if (road.getCity1() == currentCityId && !MarkedCities.contains(road.getCity2()) &&
            !citiesToVisit.contains(road.getCity2()))  {
          citiesToVisit.add(road.getCity2());
          parentMap.put(road.getCity2(), currentCityId);
        } else if (road.getCity2() == currentCityId && !MarkedCities.contains(road.getCity1()) &&
            !citiesToVisit.contains(road.getCity1())) {
          citiesToVisit.offer(road.getCity1());
          parentMap.put(road.getCity1(), currentCityId);
        }
      }
      MarkedCities.add(currentCityId);
    }

    if(!parentMap.containsKey(citiesMapNames.get(endCity).getId())){ //a.k.a on n'est jamais arrivé à la destination
      System.out.println("Il n'y a aucun chemin entre " + startCity +" et "+ endCity);
    }
    List<String> path = new ArrayList<>();
    int current = citiesMapNames.get(endCity).getId();
    int nb_roads = -1; //-1 car sinon il compte la destination comme une route...  A -> B -> C  (3 villes, mais 2 routes)
    while (current != 0) {
      nb_roads = nb_roads +1;
      path.add(citiesMap.get(current).getName());
      current = parentMap.getOrDefault(current, 0);

    }
    Collections.reverse(path);

    printItinerary(path, nb_roads);


  }

  private double calculateDistance(City city1, City city2) {
    return Util.distance(city1.getLatitude(), city1.getLongitude(), city2.getLatitude(), city2.getLongitude());
  }

  public void calculerItineraireMinimisantKm(String startCity, String endCity) {
    Map<Integer, Double> distanceMap = new HashMap<>();
    Map<Integer, Integer> parentMap = new HashMap<>();
    Set<Integer> visited = new HashSet<>();

    // Initialiasation des distance à l'infini (l'inifni ici c'est les max des valeur possible
    // sauf pour la ville de départ (distance 0 vers soit meme)
    for (City city : citiesMap.values()) {
      distanceMap.put(city.getId(), Double.MAX_VALUE);
    }
    distanceMap.put(citiesMapNames.get(startCity).getId(), 0.0);
    while (true) {
      // récupérerr la ville non visitée avec la plus petite distance jusqu'ici
      int currentCityId = -1;
      double minDistance = Double.MAX_VALUE;
      for (Map.Entry<Integer, Double> entry : distanceMap.entrySet()) {
        if (!visited.contains(entry.getKey()) && entry.getValue() < minDistance) {
          minDistance = entry.getValue();
          currentCityId = entry.getKey();
        }
      }

      if (currentCityId == -1) {
        break; // Toutes les villes accessibles ont été visitées
      }

      visited.add(currentCityId);

      for (Road road : roadsList) {
        if (road.getCity1() == currentCityId && !visited.contains(road.getCity2())) {
          double newDistance = distanceMap.get(currentCityId) + calculateDistance(citiesMap.get(currentCityId), citiesMap.get(road.getCity2()));
          if (newDistance <= distanceMap.get(road.getCity2())) {
            distanceMap.put(road.getCity2(), newDistance);
            parentMap.put(road.getCity2(), currentCityId);
          }
        }

        if (road.getCity2() == currentCityId && !visited.contains(road.getCity1())) {
          double newDistance = distanceMap.get(currentCityId) + calculateDistance(citiesMap.get(currentCityId), citiesMap.get(road.getCity1()));
          if (newDistance <= distanceMap.get(road.getCity1())) {
            distanceMap.put(road.getCity1(), newDistance);
            parentMap.put(road.getCity1(), currentCityId);
          }
        }
      }
    }


    List<String> path = new ArrayList<>();
    int current = citiesMapNames.get(endCity).getId();
    while (current != citiesMapNames.get(startCity).getId()) {
      path.add(citiesMap.get(current).getName());
      current = parentMap.getOrDefault(current, -1);
    }
    path.add(startCity);
    Collections.reverse(path);


    printItinerary(path, distanceMap.get(citiesMapNames.get(endCity).getId()));
  }



  private void printItinerary(List<String> itinerary, int nb_roads) {
    System.out.println("Itinéraire: avec un total de " + nb_roads + " route");
    double distance = 0;
    for (int i = 0; i < itinerary.size() - 1; i++) {
      String city1 = itinerary.get(i);
      String city2 = itinerary.get(i + 1);
      System.out.printf("%s -> %s (%.2f km)\n", city1, city2, calculateDistance(citiesMapNames.get(city1), citiesMapNames.get(city2)));
      distance = distance +calculateDistance(citiesMapNames.get(city1), citiesMapNames.get(city2));
    }
    System.out.println(distance);
  }

  private void printItinerary(List<String> itinerary, double distance) {
    System.out.printf("Itinéraire: avec un total de %.2f km \n", distance );
    for (int i = 0; i < itinerary.size() - 1; i++) {
      String city1 = itinerary.get(i);
      String city2 = itinerary.get(i + 1);
      System.out.printf("%s -> %s (%.2f km)\n", city1, city2, calculateDistance(citiesMapNames.get(city1), citiesMapNames.get(city2)));
    }
  }


  @Override
  public String toString() {
    String print ="";
    for (Road road : roadsList) {
      print = print + road;
    }
    return print;
  }

}

