import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class Animal implements DatabaseInterface {
  protected int id;
  protected String name;
  protected String type;

  public static final String ANIMAL_TYPE = "animal";

  public Animal(String pName) {
    if(pName.equals(""))
      throw new IllegalArgumentException("Must provide animal name!");
    this.name = pName;
    this.type = ANIMAL_TYPE;
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Network Methods

  public List<Sighting> getSightings() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM sightings WHERE animalId=:id")
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// get and set Methods

  public int getId() {
    return this.id;
  }

  public String getType() {
    return this.type;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String pName) {
    this.name = pName;
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("UPDATE animals SET name=:name WHERE id=:id AND type=:type")
        .addParameter("id", this.id)
        .addParameter("type", this.type)
        .addParameter("name", this.name)
        .executeUpdate();
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// Static Methods

  public static Animal findById(int pId) {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE id=:id AND type=:type")
        .addParameter("id", pId)
        .addParameter("type", "animal")
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Animal.class);
    }
  }

  public static List<Animal> allAnimals() {
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery("SELECT * FROM animals WHERE type=:type")
        .addParameter("type", "animal")
        .throwOnMappingFailure(false)
        .executeAndFetch(Animal.class);
    }
  }

  /////////////////////////////////////////////////////////////////////////////
  /// DatabaseInterface Methods

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      this.id = (int) con.createQuery("INSERT INTO animals (name, type) VALUES (:name, :type)", true)
        .addParameter("name", this.name)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM animals WHERE id=:id AND type=:type")
        .addParameter("id", this.id)
        .addParameter("type", this.type)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherObject) {
    if(!(otherObject instanceof Animal))
      return false;
    else {
      Animal comparisonObj = (Animal) otherObject;
      return this.id == comparisonObj.getId() && this.name.equals(comparisonObj.getName());
    }
  }
}
