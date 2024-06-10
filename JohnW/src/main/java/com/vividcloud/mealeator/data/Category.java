/*
 * Copyright (c) Dematic GmbH 2024. All rights reserved. Confidential.
 */
package com.vividcloud.mealeator.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A food category.
 *
 * @author jwilliams
 */
public enum Category {
   @JsonProperty("Appetizer") APPETIZER("Appetizer"),
   @JsonProperty("Drink") DRINK("Drink"),
   @JsonProperty("Main Course") MAIN_COURSE("Main Course"),
   @JsonProperty("Dessert") DESSERT("Dessert");

   final String label;

   private Category(String label) {
      this.label = label;
   }

   @Override
   public String toString() {
      return label;
   }
}
