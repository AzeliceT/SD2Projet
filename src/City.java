public class City {

  private static int nextId = 1;
  private int id;
  private String name;
  private String longitude;
  private String latitude;

  public City(String name, String longitude, String latitude) {
    this.name = name;
    this.longitude = longitude;
    this.latitude = latitude;
    this.id = nextId;
    nextId++;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String toString() {
    return "City{" + "id=" + id + ", name='" + name + '\'' + ", longitude='" + longitude + '\''
        + ", latitude='" + latitude + '\'' + '}' + "\n";
  }
}
