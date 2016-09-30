import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.*;
import org.sql2o.*;

public class LocationTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void getId_returnsIdOfLocation_int() {
    Location testLocation = new Location("By the tree");
    testLocation.save();
    assertTrue(testLocation.getId() > 0);
  }

  @Test
  public void getName_returnsNameOfLocation_String() {
    Location testLocation = new Location("By the tree");
    testLocation.save();
    assertEquals("By the tree", testLocation.getName());
  }

  @Test
  public void setName_changesNameOfLocation_void() {
    Location testLocation = new Location("By the tree");
    testLocation.save();
    testLocation.setName("Near the other tree");
    assertEquals("Near the other tree", testLocation.getName());
  }

  @Test
  public void findById_returnsInstanceOfLocationById_Location() {
    Location testLocation1 = new Location("By the tree");
    testLocation1.save();
    Location testLocation2 = new Location("Near the other tree");
    testLocation2.save();
    Location object1FoundById = Location.findById(testLocation1.getId());
    Location object2FoundById = Location.findById(testLocation2.getId());
    assertTrue(testLocation1.equals(object1FoundById));
    assertTrue(testLocation2.equals(object2FoundById));
  }

  @Test
  public void allLocations_returnsAllInstancesOfLocation_ArrayList() {
    Location testLocation1 = new Location("By the tree");
    testLocation1.save();
    Location testLocation2 = new Location("Near the other tree");
    testLocation2.save();
    Location object1FoundByAll = Location.allLocations().get(0);
    Location object2FoundByAll = Location.allLocations().get(1);
    assertTrue(testLocation1.equals(object1FoundByAll));
    assertTrue(testLocation2.equals(object2FoundByAll));
  }

  @Test
  public void delete_deletesInstanceOfLocation_void() {
    Location testLocation = new Location("By the tree");
    testLocation.save();
    testLocation.delete();
    assertEquals(0, Location.allLocations().size());
  }

}
