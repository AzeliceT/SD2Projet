public class City {

  private int id;
  private String name;
  private double latitude;
  private double longitude;

  public City(int id, String name, double latitude,  double longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
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


  public String toString() {
    return "City{" + "id=" + id + ", name='" + name + '\'' + ", longitude='" + longitude + '\''
        + ", latitude='" + latitude + '\'' + '}' + "\n";
  }
}
