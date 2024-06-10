/*
 * Copyright (c) Dematic GmbH 2024. All rights reserved. Confidential.
 */
package com.vividcloud.mealeator.data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Limits what foods can be combined to produce a meal.
 *
 * @author jwilliams
 */
public class UserConstraints implements JsonBean {
   int budget;

   @JsonProperty("allergy")
   List<String> allergies;

   int calorieLimit;

   public UserConstraints() {
      this(0, Collections.emptyList(), 0);
   }

   public UserConstraints(int budget, List<String> allergies, int calorieLimit) {
      this.budget = budget;
      this.allergies = allergies;
      this.calorieLimit = calorieLimit;
   }

   public int getBudget() {
      return budget;
   }

   public void setBudget(int budget) {
      this.budget = budget;
   }

   public List<String> getAllergies() {
      return allergies;
   }

   public void setAllergies(List<String> allergies) {
      this.allergies = allergies;
   }

   public int getCalorieLimit() {
      return calorieLimit;
   }

   public void setCalorieLimit(int calorieLimit) {
      this.calorieLimit = calorieLimit;
   }

   @Override
   public void optimize() {
      // nothing in the constraints need optimizing
      // downcase all the allergies
      allergies.replaceAll(String::toLowerCase);
   }

   public boolean isAllergicTo(List<String> ingredients) {
      // find the first  item the constraint is allergic to
      // if not null, then return true
      if (ingredients == null || ingredients.isEmpty()) return false;

      // This is a little simplistic. For example, if someone is allergic to cheese
      // then 'parmesan cheese' will not get caught.
      // We could break ingredients by word boundaries, but then we'd run into problems
      // if someone was allergic to 'green peppers', but not 'red peppers'.
      // For now, keep it simple.
      return allergies.stream()
            .anyMatch(ingredients::contains);
   }
}
