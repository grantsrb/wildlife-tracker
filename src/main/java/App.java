import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request,response) -> {
      return new ModelAndView(homepageModel(), layout);
    }, new VelocityTemplateEngine());

  }

  public static Map<String,Object> homepageModel() {
    Map<String,Object> model = new HashMap<>();
    model.put("Animal", Animal.class);
    model.put("Location", Location.class);
    model.put("Ranger", Ranger.class);
    model.put("sightings", Sighting.allSightings());
    model.put("success", false);
    model.put("template", "templates/index.vtl");
    return model;
  }
}
