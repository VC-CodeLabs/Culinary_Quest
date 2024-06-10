
package com.vividcloud.mealeator.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.jr.ob.JSON;

/**
 * A meal contains a specific combination of appetizer, drink, mainCourse and dessert from a menu.
 *
 * @author jwilliams
 */
public class Meal  {
   Menu menu;
   int appetizer;
   int drink;
   int mainCourse;
   int dessert;

   public Meal() {

   }

   public Meal(Menu menu) {
      this.menu = menu;
      this.appetizer = 0;
      this.drink = 0;
      this.mainCourse = 0;
      this.dessert = 0;
   }

   public Meal(Meal meal) {
      this.menu = meal.menu;
      setAppetizer(meal.getAppetizer());
      setDrink(meal.getDrink());
      setMainCourse(meal.getMainCourse());
      setDessert(meal.getDessert());
   }

   public int getAppetizer() {
      return appetizer;
   }

   public void setAppetizer(int appetizer) {
      this.appetizer = appetizer;
   }

   public int getDrink() {
      return drink;
   }

   public void setDrink(int drink) {
      this.drink = drink;
   }

   public int getMainCourse() {
      return mainCourse;
   }

   public void setMainCourse(int mainCourse) {
      this.mainCourse = mainCourse;
   }

   public int getDessert() {
      return dessert;
   }

   public void setDessert(int dessert) {
      this.dessert = dessert;
   }

   public Collection<Optional<Food>> getSelectedFoods() {
      List<Optional<Food>> selection = new ArrayList<>();
      selection.add(menu.getFood(Category.APPETIZER, appetizer));
      selection.add(menu.getFood(Category.DRINK, drink));
      selection.add(menu.getFood(Category.MAIN_COURSE, mainCourse));
      selection.add(menu.getFood(Category.DESSERT, dessert));
      return selection;
   }

   public int getTotalCost() {
      return getSelectedFoods().stream()
            .filter(Optional::isPresent)
            .map(of -> of.get().getCost())
            .reduce(0, Integer::sum);
   }

   public int getTotalSatisfaction() {
      return getSelectedFoods().stream()
            .filter(Optional::isPresent)
            .map(of -> of.get().getSatisfaction())
            .reduce(0, Integer::sum);
   }

   public int getTotalCalories() {
      return getSelectedFoods().stream()
            .filter(Optional::isPresent)
            .map(of -> of.get().getCalories())
            .mapToInt(Integer::intValue)
            .sum();
   }

   public String toJson() throws IOException {
      return JSON.std
            .with(JSON.Feature.PRETTY_PRINT_OUTPUT)
            .asString(new LinkedHashMap<String, Object>() {{
               put("selectedFoods", getSelectedFoods().stream()
                     .filter(Optional::isPresent)
                     .map(of -> {
                        LinkedHashMap<String, Object> lhm = new LinkedHashMap<>();
                        lhm.put("category", of.get().getCategory());
                        lhm.put("name", of.get().getName());
                        return lhm;
                     })
                     .collect(Collectors.toList()));
               put("totalCost", getTotalCost());
               put("totalSatisfaction", getTotalSatisfaction());
               put("totalCalories", getTotalCalories());
            }});
   }
}
