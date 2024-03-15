public class Road {

  private int idSource;
  private int idDestination;

  public Road(int idSource, int idDestination) {
    this.idSource = idSource;
    this.idDestination = idDestination;
  }

  public int getIdSource() {
    return idSource;
  }

  public void setIdSource(int idSource) {
    this.idSource = idSource;
  }

  public int getIdDestination() {
    return idDestination;
  }

  public void setIdDestination(int idDestination) {
    this.idDestination = idDestination;
  }
}
