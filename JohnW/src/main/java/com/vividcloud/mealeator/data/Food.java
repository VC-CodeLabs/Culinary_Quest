/*
 * Copyright (c) Dematic GmbH 2024. All rights reserved. Confidential.
 */
package com.vividcloud.mealeator.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A single food item in a menu or meal.
 *
 * @author jwilliams
 */
public class Food {
   private String name;
   private int cost;
   private int satisfaction;
   private Category category;
   private List<String> ingredients;
   private int calories;

   public Food() {}

   public Food(String name, int cost, int satisfaction, Category category) {
      this.name = name;
      this.cost = cost;
      this.satisfaction = satisfaction;
      this.category = category;
      this.ingredients = new ArrayList<>();
      this.calories = 0;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getCost() {
      return cost;
   }

   public void setCost(int cost) {
      this.cost = cost;
   }

   public int getSatisfaction() {
      return satisfaction;
   }

   public void setSatisfaction(int satisfaction) {
      this.satisfaction = satisfaction;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   /**
    * Convert ingredients to lower case to make allergy test easier
    */
   public Food optimize() {
      ingredients.replaceAll(String::toLowerCase);
      return this;
   }

   public List<String> getIngredients() {
      return ingredients;
   }

   public void setIngredients(List<String> ingredients) {
      this.ingredients = ingredients;
   }

   public int getCalories() {
      return calories;
   }

   public void setCalories(int calories) {
      this.calories = calories;
   }
}
