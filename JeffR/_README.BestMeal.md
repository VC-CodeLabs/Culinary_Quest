# Best Meal

**Overview**</br>
You're looking to help plan a perfect dining experience within a given budget. You will be provided with a menu and a budget. The menu will include various dishes across four categories: Appetizers, Drinks, Main Courses, and Desserts. Each dish has an associated cost and satisfaction level. You must select at least one item from each category to maximize total satisfaction without exceeding the budget.

**Challenge Details**</br>
**Input** Your solution should read from an external file named **menu.json** that contains:
* An array of dish objects with properties: name, cost, satisfaction, and category.
* An integer budget.</br>

**Output** Your program should output:
* A list of selected dishes.
* The total cost and total satisfaction of the selected dishes.</br>

**Requirements**
* Each menu item must be from at least one of the four categories: appetizer, Drink, Main Course, and Dessert.
* The total cost of selected items must not exceed the budget.
* Maximize the total satisfaction score of the selected items.</br>

**Constraints**
* The number of items per category on the menu can range from 0 to 50.
* The dishes in the menu.json file may appear in any order.
* All dollar amounts will be integers.

**Submission Details**
* Code in any language of your choosing.
* Please have solutions to me by the end of the day on Monday; if you need a bit more time, just reach out and let me know, and we'll discuss it.

**Scoring**</br>

**Correctness and Completeness** (60 points)</br>
* Full marks are awarded if the solution passes all provided test cases, including edge cases.</br>
* Partial marks are given if the solution passes only some test cases.</br>
* No points are awarded if the solution fails to compile or crashes on most test cases.</br>

**Code Quality and Style** (30 points)</br>
* Full marks are awarded for well-structured code with a summary outlining your approach, clear, meaningful comments and good variable naming.</br>
* Partial marks are given if the code is mostly well-written but lacks comments or has minor style issues.</br>
* Few or no points are awarded for code that is difficult to read, poorly structured, or lacking documentation.</br>

**Performance and Efficiency** (10 points)</br>
* Full marks are awarded if the solution’s runtime is the fastest of all submissions.</br>
* Partial marks are given if the solution is not the slowest.</br>
* No points are awarded if the solution exhibits poor performance - it is the slowest solution.</br>


**Examples**</br>
```
menu.json
{
  "foods": [
    {"name": "Fried Calamari", "cost": 6, "satisfaction": 5, "category": "Appetizer"},
    {"name": "Spinach Dip", "cost": 5, "satisfaction": 3, "category": "Appetizer"},
    {"name": "Bruschetta", "cost": 4, "satisfaction": 4, "category": "Appetizer"},
    {"name": "Chicken Wings", "cost": 7, "satisfaction": 6, "category": "Appetizer"},
    {"name": "Garlic Bread", "cost": 3, "satisfaction": 2, "category": "Appetizer"},
    {"name": "Soda", "cost": 1, "satisfaction": 1, "category": "Drink"},
    {"name": "Coffee", "cost": 2, "satisfaction": 2, "category": "Drink"},
    {"name": "Beer", "cost": 3, "satisfaction": 2, "category": "Drink"},
    {"name": "Wine", "cost": 5, "satisfaction": 4, "category": "Drink"},
    {"name": "Cocktail", "cost": 6, "satisfaction": 5, "category": "Drink"},
    {"name": "Steak", "cost": 12, "satisfaction": 10, "category": "Main Course"},
    {"name": "Salmon", "cost": 11, "satisfaction": 9, "category": "Main Course"},
    {"name": "Lasagna", "cost": 8, "satisfaction": 7, "category": "Main Course"},
    {"name": "Burger", "cost": 6, "satisfaction": 5, "category": "Main Course"},
    {"name": "Vegetable Stir Fry", "cost": 7, "satisfaction": 6, "category": "Main Course"},
    {"name": "Cheesecake", "cost": 4, "satisfaction": 4, "category": "Dessert"},
    {"name": "Apple Pie", "cost": 3, "satisfaction": 3, "category": "Dessert"},
    {"name": "Ice Cream", "cost": 2, "satisfaction": 2, "category": "Dessert"},
    {"name": "Chocolate Cake", "cost": 5, "satisfaction": 5, "category": "Dessert"},
    {"name": "Tiramisu", "cost": 6, "satisfaction": 6, "category": "Dessert"}
  ],
  "budget": 25
}


Output:
{
  "selectedFoods": ["Bruschetta", "Soda", "Lasagna", "Cheesecake"],
  "totalCost": 19,
  "totalSatisfaction": 18
}
```

```
menu.json

{
  "foods": [
    {"name": "Spring Rolls", "cost": 10, "satisfaction": 8, "category": "Appetizer"},
    {"name": "Cola", "cost": 5, "satisfaction": 3, "category": "Drink"},
    {"name": "Ribeye Steak", "cost": 25, "satisfaction": 20, "category": "Main Course"},
    {"name": "Chocolate Mousse", "cost": 15, "satisfaction": 12, "category": "Dessert"}
  ],
  "budget": 20
}


Output:
{
  "error": "Not enough money, you is broke, go work some more before wasting your cash on food!"
}

```

