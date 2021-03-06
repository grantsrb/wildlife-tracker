import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ranger implements DatabaseInterface{
  private int id;
  private String name;
  private String badgeId;

  public Ranger(String pName, String pBadgeId) {
    if(pName.equals("") || pBadgeId.equals(""))
      throw new IllegalArgumentException("Rangers must have a name and badge ID!");
    this.name = pName;
    this.badgeId = pBadgeId;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Network Methods

  public List<Sighting> getSightings() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE rangerId=:id")
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

  public String getBadgeId() {
    return this.badgeId;
  }

  public void setName(String pName) {
    if (pName.equals(""))
      throw new IllegalArgumentException("Rangers must have a name!");
    this.name = pName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE rangers SET name=:name WHERE id=:id")
        .addParameter("id", this.id)
        .addParameter("name", this.name)
        .executeUpdate();
    }
  }

  public void setBadgeId(String pBadgeId) {
    if (pBadgeId.equals(""))
      throw new IllegalArgumentException("Rangers must have a badgeId!");
    this.badgeId = pBadgeId;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE rangers SET badgeId=:badgeId WHERE id=:id")
        .addParameter("id", this.id)
        .addParameter("badgeId", this.badgeId)
        .executeUpdate();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Static Methods

  public static Ranger findById(int pId) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM rangers WHERE id=:id")
        .addParameter("id", pId)
        .executeAndFetchFirst(Ranger.class);
    }
  }

  public static List<Ranger> allRangers() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM rangers")
        .executeAndFetch(Ranger.class);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// DatabaseInterface Methods

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO rangers (name, badgeId) VALUES (:name, :badgeId)", true)
        .addParameter("name", this.name)
        .addParameter("badgeId", this.badgeId)
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
      con.createQuery("DELETE FROM rangers WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if(!(otherObject instanceof Ranger))
      return false;
    else {
      Ranger comparisonObj = (Ranger) otherObject;
      return this.id == comparisonObj.getId() && this.name.equals(comparisonObj.getName()) && this.badgeId.equals(comparisonObj.getBadgeId());
    }
  }


}
