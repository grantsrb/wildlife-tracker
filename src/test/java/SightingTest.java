import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.*;

public class SightingTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void getId_returnsIdOfSighting_int() {
    Sighting testSighting = new Sighting(1,1,1);
    testSighting.save();
    assertTrue(testSighting.getId() > 0);
  }

  @Test
  public void getAnimalId_returnsAnimalIdOfSighting_int() {
    Sighting testSighting = new Sighting(1,1,1);
    testSighting.save();
    assertEquals(1, testSighting.getAnimalId());
  }

  @Test
  public void getLocationId_returnsLocationIdOfSighting_int() {
    Sighting testSighting = new Sighting(1,1,1);
    testSighting.save();
    assertEquals(1, testSighting.getLocationId());
  }

  @Test
  public void getRangerId_returnsRangerIdOfSighting_int() {
    Sighting testSighting = new Sighting(1,1,1);
    testSighting.save();
    assertEquals(1, testSighting.getRangerId());
  }

  @Test
  public void findById_returnsInstanceOfSightingById_Sighting() {
    Sighting testSighting1 = new Sighting(1,1,1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting(2,2,2);
    testSighting2.save();
    Sighting object1FoundById = Sighting.findById(testSighting1.getId());
    Sighting object2FoundById = Sighting.findById(testSighting2.getId());
    assertTrue(testSighting1.equals(object1FoundById));
    assertTrue(testSighting2.equals(object2FoundById));
  }

  @Test
  public void allSightings_returnsAllInstancesOfSighting_ArrayList() {
    Sighting testSighting1 = new Sighting(1,1,1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting(2,2,2);
    testSighting2.save();
    Sighting object1FoundByAll = Sighting.allSightings().get(0);
    Sighting object2FoundByAll = Sighting.allSightings().get(1);
    assertTrue(testSighting1.equals(object1FoundByAll));
    assertTrue(testSighting2.equals(object2FoundByAll));
  }

  @Test
  public void getTimeSpotted_returnsTimeOfSighting_int() {
    Sighting testSighting = new Sighting(1,1,1);
    testSighting.save();
    Sighting objectFoundById = Sighting.findById(testSighting.getId());
    assertEquals(objectFoundById.getTimeSpotted(), testSighting.getTimeSpotted());
  }
}
