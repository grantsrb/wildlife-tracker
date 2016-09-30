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

    get("/sightings/new", (request,response) -> {
      Map<String, Object> model = homepageModel();
      int locationId = Integer.parseInt(request.queryParams("location"));
      int animalId = Integer.parseInt(request.queryParams("animal"));
      int rangerId = Integer.parseInt(request.queryParams("ranger"));
      if(animalId == 0) {
        String animalName = request.queryParams("animal-entry");
        try {
          Animal newAnimal = new Animal(animalName);
          newAnimal.save();
          Sighting newSighting = new Sighting(newAnimal.getId(), locationId, rangerId);
          newSighting.save();
          model.put("success", true);
        } catch (IllegalArgumentException exception) {}
      } else {
        Sighting newSighting = new Sighting(animalId, locationId, rangerId);
        newSighting.save();
        model.put("success", true);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/delete", (request,response) -> {
      Map<String, Object> model = homepageModel();
      int sightingId = Integer.parseInt(request.params(":id"));
      model.put("sighting", Sighting.findById(sightingId));
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:id", (request,response) -> {
      int sightingId = Integer.parseInt(request.queryParams("id"));
      Sighting.findById(sightingId).delete();
      return new ModelAndView(homepageModel(), layout);
    }, new VelocityTemplateEngine());

    post("/animals/new-animal", (request,response) -> {
      Map<String, Object> model = animalModel();
      String name = request.queryParams("name");
      String type = request.queryParams("type");
      if(type.equals("animal")) {
        try{
          Animal newAnimal = new Animal(name);
          newAnimal.save();
          model = animalModel();
          model.put("success", true);
        } catch (IllegalArgumentException exception) {
          model.put("exception", exception);
        }
      } else if (type.equals("endangered")) {
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        try{
          EndangeredAnimal newEndangeredAnimal = new EndangeredAnimal(name, health, age);
          newEndangeredAnimal.save();
          model = animalModel();
          model.put("success", true);
        } catch (IllegalArgumentException exception) {
          model.put("exception", exception);
        }
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/type", (request,response) -> {
      Map<String, Object> model = animalModel();
      String type = request.queryParams("type");
      model.put("type", type);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals", (request,response) -> {
      return new ModelAndView(animalModel(), layout);
    }, new VelocityTemplateEngine());

    post("/locations/new-location", (request,response) -> {
      Map<String, Object> model = locationModel();
      String name = request.queryParams("name");
      try {
        Location newLocation = new Location(name);
        newLocation.save();
        model = locationModel();
      } catch (IllegalArgumentException exception) {
        model.put("exception", exception);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/locations", (request,response) -> {
      return new ModelAndView(locationModel(), layout);
    }, new VelocityTemplateEngine());

    post("/rangers/new-ranger", (request,response) -> {
      Map<String, Object> model = rangerModel();
      String name = request.queryParams("name");
      String badgeId = request.queryParams("badgeId");
      try {
        Ranger newRanger = new Ranger(name, badgeId);
        newRanger.save();
        model = rangerModel();
      } catch (IllegalArgumentException exception) {
        model.put("exception", exception);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/rangers", (request,response) -> {
      return new ModelAndView(rangerModel(), layout);
    }, new VelocityTemplateEngine());
  }



  public static Map<String,Object> homepageModel() {
    Map<String,Object> model = new HashMap<>();
    model.put("Animal", Animal.class);
    model.put("EndangeredAnimal", EndangeredAnimal.class);
    model.put("Location", Location.class);
    model.put("Ranger", Ranger.class);
    model.put("sightings", Sighting.allSightings());
    model.put("success", false);
    model.put("template", "templates/index.vtl");
    return model;
  }

  public static Map<String,Object> rangerModel() {
    Map<String, Object> model = homepageModel();
    model.put("rangers", Ranger.allRangers());
    model.put("exception", "");
    model.put("template", "templates/rangers.vtl");
    return model;
  }


  public static Map<String,Object> locationModel() {
    Map<String, Object> model = homepageModel();
    model.put("locations", Location.allLocations());
    model.put("exception", "");
    model.put("template", "templates/locations.vtl");
    return model;
  }

  public static Map<String,Object> animalModel() {
    Map<String, Object> model = homepageModel();
    model.put("animals", Animal.allAnimals());
    model.put("type", "none");
    model.put("template", "templates/animals.vtl");
    return model;
  }
}
