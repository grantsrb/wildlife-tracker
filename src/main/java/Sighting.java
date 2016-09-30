import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class Sighting implements DatabaseInterface {
  private int id;
  private int animalId;
  private int locationId;
  private int rangerId;
  private Timestamp timeSpotted;

  public Sighting(int pAnimalId, int pLocationId, int pRangerId) {
    this.animalId = pAnimalId;
    this.locationId = pLocationId;
    this.rangerId = pRangerId;
    this.timeSpotted = new Timestamp(new Date().getTime());
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public int getAnimalId() {
    return this.animalId;
  }

  public int getLocationId() {
    return this.locationId;
  }

  public int getRangerId() {
    return this.rangerId;
  }

  public String getTimeSpotted() {
    return DateFormat.getDateTimeInstance().format(this.timeSpotted);
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Static Methods

  public static Sighting findById(int pId) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(Sighting.class);
    }
  }

  public static List<Sighting> allSightings() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings")
        .executeAndFetch(Sighting.class);
    }
  }

  // public static List<Sighting> allAnimalSightings() {
  //   try (Connection con = DB.sql2o.open()) {
  //     return con.createQuery("SELECT * FROM sightings")
  //       .executeAndFetch(Sighting.class);
  //   }
  // }
  //
  // public static List<Sighting> allEndangeredSightings() {
  //   try (Connection con = DB.sql2o.open()) {
  //     return con.createQuery("SELECT * FROM sightings")
  //       .executeAndFetch(Sighting.class);
  //   }
  // }

  /////////////////////////////////////////////////////////////////////////////
  /// DatabaseInterface Methods

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO sightings (animalId, locationId, rangerId, timeSpotted) VALUES (:animalId, :locationId, :rangerId, :timeSpotted)", true)
        .addParameter("animalId", this.animalId)
        .addParameter("locationId", this.locationId)
        .addParameter("rangerId", this.rangerId)
        .addParameter("timeSpotted", this.timeSpotted)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM sightings WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if(!(otherObject instanceof Sighting))
      return false;
    else {
      Sighting comparisonObj = (Sighting) otherObject;
      return this.id == comparisonObj.getId() && this.animalId == comparisonObj.getAnimalId() && this.locationId == comparisonObj.getLocationId() && this.rangerId == comparisonObj.getRangerId() && DateFormat.getDateTimeInstance().format(this.timeSpotted).equals(comparisonObj.getTimeSpotted());
    }
  }

}
