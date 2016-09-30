import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM animals *").executeUpdate();
      con.createQuery("DELETE FROM sightings *").executeUpdate();
      con.createQuery("DELETE FROM rangers *").executeUpdate();
      con.createQuery("DELETE FROM locations *").executeUpdate();
    }
  }

}
