public class Road {

  private int city1;
  private int city2;

  public Road(int idSource, int idDestination) {
    this.city1 = idSource;
    this.city2 = idDestination;
  }

  public int getCity1() {
    return city1;
  }

  public void setCity1(int city1) {
    this.city1 = city1;
  }

  public int getCity2() {
    return city2;
  }

  public void setCity2(int city2) {
    this.city2 = city2;
  }


}
