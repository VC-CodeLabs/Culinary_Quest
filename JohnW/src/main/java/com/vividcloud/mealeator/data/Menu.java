
package com.vividcloud.mealeator.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A menu is a collection of all food that might be used to build a meal.
 *
 * @author jwilliams
 */
public class Menu implements JsonBean {
   List<Food> foods = new ArrayList<>();

   @JsonIgnore
   Map<Category, List<Food>> courses = new HashMap<>();

   public Menu() {
   }

   public List<Food> getFoods() {
      return foods;
   }

   public void setFoods(List<Food> foods) {
      this.foods = foods;
   }

   public void optimize() {

      courses.put(Category.APPETIZER, byCategory(Category.APPETIZER));
      courses.put(Category.DRINK, byCategory(Category.DRINK));
      courses.put(Category.MAIN_COURSE, byCategory(Category.MAIN_COURSE));
      courses.put(Category.DESSERT, byCategory(Category.DESSERT));

      getFoods().stream().map(Food::optimize);
   }

   public List<Food> getFoods(Category category) {
      return courses.get(category);
   }

   public Optional<Food> getFood(Category category, int index) {
      if (index < getFoods(category).size()) {
         return Optional.of(getFoods(category).get(index));
      }
      return Optional.empty();
   }

   protected List<Food> byCategory(Category category) {
      return foods.stream()
            .filter(f -> f.getCategory() == category)
            .sorted((o1, o2) -> o2.getSatisfaction() - o1.getSatisfaction())
            .collect(Collectors.toList());
   }

   /**
    * Remove any foods from the menu that the user is allergic to
    * @param constraints Contains User allergies
    */
   public void filterAllergies(UserConstraints constraints) {

      courses.put(Category.APPETIZER, filterCategory(Category.APPETIZER, constraints));
      courses.put(Category.DRINK, filterCategory(Category.DRINK, constraints));
      courses.put(Category.MAIN_COURSE, filterCategory(Category.MAIN_COURSE, constraints));
      courses.put(Category.DESSERT, filterCategory(Category.DESSERT, constraints));
   }

   public List<Food> filterCategory(Category category, UserConstraints constraints) {
      return courses.get(category).stream()
            .filter(f -> !constraints.isAllergicTo(f.getIngredients()))
            .collect(Collectors.toList());
   }
}
