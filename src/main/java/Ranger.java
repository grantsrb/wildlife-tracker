import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Ranger implements DatabaseInterface{
  private int id;
  private String name;
  private String badgeId;

  public Ranger(String pName, String pBadgeId) {
    this.name = pName;
    this.badgeId = pBadgeId;
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
    this.name = pName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE rangers SET name=:name WHERE id=:id")
        .addParameter("id", this.id)
        .addParameter("name", this.name)
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
