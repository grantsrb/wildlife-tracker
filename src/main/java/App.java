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

    post("/sightings/new", (request,response) -> {
      Map<String, Object> model = homepageModel();
      int locationId = Integer.parseInt(request.queryParams("location"));
      int rangerId = Integer.parseInt(request.queryParams("ranger"));
      String animalIntel = request.queryParams("animal");
      if(animalIntel.equals("0")) {
        String animalName = request.queryParams("animal-entry");
        try {
          Animal newAnimal = new Animal(animalName);
          newAnimal.save();
          Sighting newSighting = new Sighting(newAnimal.getId(), newAnimal.getType(), locationId, rangerId);
          newSighting.save();
          model=homepageModel();
          model.put("success", true);
        } catch (IllegalArgumentException exception) {}
      } else {
        if(animalIntel.charAt(0) == 'a') {
          int animalId = Integer.parseInt(animalIntel.substring(2));
          Animal animal = Animal.findById(animalId);
          Sighting newSighting = new Sighting(animalId, animal.getType(), locationId, rangerId);
          newSighting.save();
        } else {
          int animalId = Integer.parseInt(animalIntel.substring(2));
          EndangeredAnimal endangered = EndangeredAnimal.findById(animalId);
          Sighting newSighting = new Sighting(animalId, endangered.getType(), locationId, rangerId);
          newSighting.save();
        }
        model=homepageModel();
        model.put("success", true);
      }
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/delete", (request,response) -> {
      int sightingId = Integer.parseInt(request.queryParams("id"));
      Sighting.findById(sightingId).delete();
      return new ModelAndView(homepageModel(), layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:id", (request,response) -> {
      Map<String,Object> model = homepageModel();
      int sightingId = Integer.parseInt(request.params(":id"));
      model.put("sighting", Sighting.findById(sightingId));
      model.put("template", "templates/sighting-update.vtl");
      return new ModelAndView(model, layout);
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

    post("/animals/update/:type", (request, response) -> {
      Map<String, Object> model = animalModel();
      int animalId = Integer.parseInt(request.queryParams("id"));
      String name = request.queryParams("name");
      String type = request.params(":type");
      if(type.equals("animal")) {
        try {
          Animal animal = Animal.findById(animalId);
          animal.setName(name);
          model.put("animal", animal);
        } catch (IllegalArgumentException exception) {
          model.put("exception", exception);
        }
      } else {
        try {
          EndangeredAnimal animal = EndangeredAnimal.findById(animalId);
          animal.setName(name);
          model.put("animal", animal);
        } catch (IllegalArgumentException exception) {
          model.put("exception", exception);
        }
      }
      model.put("template", "templates/animal-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/type", (request,response) -> {
      Map<String, Object> model = animalModel();
      String type = request.queryParams("type");
      model.put("type", type);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/delete/:type", (request,response) -> {
      int animalId = Integer.parseInt(request.queryParams("id"));
      String type = request.params(":type");
      if(type.equals("animal"))
        Animal.findById(animalId).delete();
      else
        EndangeredAnimal.findById(animalId).delete();
      return new ModelAndView(animalModel(), layout);
    }, new VelocityTemplateEngine());

    get("/animals/:id/:type", (request,response) -> {
      Map<String,Object> model = animalModel();
      String type = request.params(":type");
      int animalId= Integer.parseInt(request.params(":id"));
      if(type.equals("animal"))
        model.put("animal", Animal.findById(animalId));
      else
        model.put("animal", EndangeredAnimal.findById(animalId));
      model.put("template", "templates/animal-update.vtl");
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

    post("/locations/update", (request, response) -> {
      Map<String, Object> model = locationModel();
      int locationId = Integer.parseInt(request.queryParams("id"));
      String name = request.queryParams("name");
      try {
        Location location = Location.findById(locationId);
        location.setName(name);
        model.put("location", location);
      } catch (IllegalArgumentException exception) {
        model.put("exception", exception);
      }
      model.put("template", "templates/location-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/locations/delete", (request,response) -> {
      int locationId = Integer.parseInt(request.queryParams("id"));
      Location.findById(locationId).delete();
      return new ModelAndView(locationModel(), layout);
    }, new VelocityTemplateEngine());

    get("/locations/:id", (request,response) -> {
      Map<String,Object> model = locationModel();
      int locationId = Integer.parseInt(request.params(":id"));
      model.put("location", Location.findById(locationId));
      model.put("template", "templates/location-update.vtl");
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

    post("/rangers/update", (request, response) -> {
      int rangerId = Integer.parseInt(request.queryParams("id"));
      String name = request.queryParams("name");
      String badgeId = request.queryParams("badgeId");
      Ranger ranger = Ranger.findById(rangerId);
      if(!name.equals(""))
        ranger.setName(name);
      if(!badgeId.equals(""))
        ranger.setBadgeId(badgeId);
      Map<String, Object> model = rangerModel();
      model.put("ranger", ranger);
      model.put("template", "templates/ranger-update.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/rangers/delete", (request,response) -> {
      int rangerId = Integer.parseInt(request.queryParams("id"));
      Ranger.findById(rangerId).delete();
      return new ModelAndView(rangerModel(), layout);
    }, new VelocityTemplateEngine());

    get("/rangers/:id", (request,response) -> {
      Map<String,Object> model = rangerModel();
      int rangerId = Integer.parseInt(request.params(":id"));
      model.put("ranger", Ranger.findById(rangerId));
      model.put("template", "templates/ranger-update.vtl");
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
    model.put("animals", Animal.allAnimals());
    model.put("endangereds", EndangeredAnimal.allEndangeredAnimals());
    model.put("Location", Location.class);
    model.put("locations", Location.allLocations());
    model.put("Ranger", Ranger.class);
    model.put("rangers", Ranger.allRangers());
    model.put("animalSightings", Sighting.allAnimalSightings());
    model.put("endangeredSightings", Sighting.allEndangeredSightings());
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
    model.put("type", "none");
    model.put("exception", "");
    model.put("template", "templates/animals.vtl");
    return model;
  }
}
