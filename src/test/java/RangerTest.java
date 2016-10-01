import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.*;
import org.sql2o.*;

public class RangerTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void getId_returnsIdOfRanger_int() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    assertTrue(testRanger.getId() > 0);
  }

  @Test
  public void getName_returnsNameOfRanger_String() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    assertEquals("Longmire", testRanger.getName());
  }

  @Test
  public void getBadgeId_returnsBadgeIdOfRanger_String() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    assertEquals("007", testRanger.getBadgeId());
  }

  @Test
  public void setName_changesNameOfRanger_void() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    testRanger.setName("John Brown");
    assertEquals("John Brown", testRanger.getName());
  }

  @Test
  public void findById_returnsInstanceOfRangerById_Ranger() {
    Ranger testRanger1 = new Ranger("Longmire", "007");
    testRanger1.save();
    Ranger testRanger2 = new Ranger("John Brown", "009");
    testRanger2.save();
    Ranger object1FoundById = Ranger.findById(testRanger1.getId());
    Ranger object2FoundById = Ranger.findById(testRanger2.getId());
    assertTrue(testRanger1.equals(object1FoundById));
    assertTrue(testRanger2.equals(object2FoundById));
  }

  @Test
  public void allRangers_returnsAllInstancesOfRanger_ArrayList() {
    Ranger testRanger1 = new Ranger("Longmire", "007");
    testRanger1.save();
    Ranger testRanger2 = new Ranger("John Brown", "009");
    testRanger2.save();
    Ranger object1FoundByAll = Ranger.allRangers().get(0);
    Ranger object2FoundByAll = Ranger.allRangers().get(1);
    assertTrue(testRanger1.equals(object1FoundByAll));
    assertTrue(testRanger2.equals(object2FoundByAll));
  }

  @Test
  public void delete_deletesInstanceOfRanger_void() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    testRanger.delete();
    assertEquals(0, Ranger.allRangers().size());
  }

  @Test
  public void getSightings_returnsAllSightingInstancesAttachedToThisRanger_ArrayList() {
    Ranger testRanger = new Ranger("Longmire", "007");
    testRanger.save();
    Sighting testSighting1 = new Sighting(1, "animal", 1, testRanger.getId());
    testSighting1.save();
    Sighting testSighting2 = new Sighting(1, "animal", 1, testRanger.getId());
    testSighting2.save();
    Sighting foundByMethod1 = testRanger.getSightings().get(0);
    Sighting foundByMethod2 = testRanger.getSightings().get(1);
    assertTrue(testSighting1.equals(foundByMethod1));
    assertTrue(testSighting2.equals(foundByMethod2));
  }

}
