import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.*;
import org.sql2o.*;

public class AnimalTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Test
  public void getId_returnsIdOfAnimal_int() {
    Animal testAnimal = new Animal("Lion");
    testAnimal.save();
    assertTrue(testAnimal.getId() > 0);
  }

  @Test
  public void getName_returnsNameOfAnimal_String() {
    Animal testAnimal = new Animal("Lion");
    testAnimal.save();
    assertEquals("Lion", testAnimal.getName());
  }

  @Test
  public void setName_changesNameOfAnimal_void() {
    Animal testAnimal = new Animal("Lion");
    testAnimal.save();
    testAnimal.setName("Jaguar");
    assertEquals("Jaguar", testAnimal.getName());
  }

  @Test
  public void findById_returnsInstanceOfAnimalById_Animal() {
    Animal testAnimal1 = new Animal("Lion");
    testAnimal1.save();
    Animal testAnimal2 = new Animal("Jaguar");
    testAnimal2.save();
    Animal object1FoundById = Animal.findById(testAnimal1.getId());
    Animal object2FoundById = Animal.findById(testAnimal2.getId());
    assertTrue(testAnimal1.equals(object1FoundById));
    assertTrue(testAnimal2.equals(object2FoundById));
  }

  @Test
  public void allAnimals_returnsAllInstancesOfAnimal_ArrayList() {
    Animal testAnimal1 = new Animal("Lion");
    testAnimal1.save();
    Animal testAnimal2 = new Animal("Jaguar");
    testAnimal2.save();
    Animal object1FoundByAll = Animal.allAnimals().get(0);
    Animal object2FoundByAll = Animal.allAnimals().get(1);
    assertTrue(testAnimal1.equals(object1FoundByAll));
    assertTrue(testAnimal2.equals(object2FoundByAll));
  }

  @Test
  public void delete_deletesInstanceOfAnimal_void() {
    Animal testAnimal = new Animal("Lion");
    testAnimal.save();
    testAnimal.delete();
    assertEquals(0, Animal.allAnimals().size());
  }

  @Test
  public void getSightings_returnsAllSightingInstancesAttachedToThisAnimal_ArrayList() {
    Animal testAnimal = new Animal("Lion");
    testAnimal.save();
    Sighting testSighting1 = new Sighting(testAnimal.getId(), 1, 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting(testAnimal.getId(), 1, 1);
    testSighting2.save();
    Sighting foundByMethod1 = testAnimal.getSightings().get(0);
    Sighting foundByMethod2 = testAnimal.getSightings().get(1);
    assertTrue(testSighting1.equals(foundByMethod1));
    assertTrue(testSighting2.equals(foundByMethod2));
  }

}
