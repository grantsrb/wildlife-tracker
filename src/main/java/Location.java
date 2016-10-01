import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Location implements DatabaseInterface {
  private int id;
  private String name;

  public Location(String pName) {
    if(pName.equals(""))
      throw new IllegalArgumentException("Locations must have a name!");
    this.name = pName;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Network Methods

  public List<Sighting> getSightings() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE locationId=:id")
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String pName) {
    this.name = pName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE locations SET name=:name WHERE id=:id")
        .addParameter("id", this.id)
        .addParameter("name", this.name)
        .executeUpdate();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Static Methods

  public static Location findById(int pId) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM locations WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(Location.class);
    }
  }

  public static List<Location> allLocations() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM locations")
        .executeAndFetch(Location.class);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// DatabaseInterface Methods

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO locations (name) VALUES (:name)", true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    List<Sighting> sightings = this.getSightings();
    for (int i = 0; i < sightings.size(); i++) {
      sightings.get(0).delete();
    }
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM locations WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if(!(otherObject instanceof Location))
      return false;
    else {
      Location comparisonObj = (Location) otherObject;
      return this.id == comparisonObj.getId() && this.name.equals(comparisonObj.getName());
    }
  }


}
