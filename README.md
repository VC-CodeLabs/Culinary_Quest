# Culinary_Quest


**Objective**</br>
Elevate the "Best Meal" challenge by integrating personalized dietary constraints such as allergies and calorie limits. This scenario is designed to realistically simulate meal planning, taking into account each user's unique dietary requirements within a specified budget. Participants are encouraged to build upon solutions from the initial Best Meal challenge, enhancing and adapting them to meet these expanded criteria.

**Challenge Structure**</br>
The challenge will involve reading from two JSON files:

* menu.json - Contains the details about various dishes.
* user_constraints.json - Contains user-specific constraints such as budget, allergies, and calorie limits.
* Leave a comment in the code where I update the path for each file

**Task**</br>
Develop a program that selects an optimal set of dishes from a menu to maximize dining satisfaction, adhering to budgetary constraints, dietary restrictions, and caloric intake limitations. The selected dishes should provide the highest possible satisfaction without exceeding the budget and calorie limit, and must avoid ingredients that the user is allergic to.

**Input Files**</br>
1. menu.json</br>

```
{
  "foods": [
    {
      "name": "Fried Calamari",
      "cost": 6,
      "satisfaction": 5,
      "category": "Appetizer",
      "ingredients": ["calamari", "flour", "oil"],
      "calories": 300
    },
    {
      "name": "Bruschetta",
      "cost": 4,
      "satisfaction": 4,
      "category": "Appetizer",
      "ingredients": ["bread", "tomatoes", "garlic"],
      "calories": 150
    }
  ]
}
```
</br>
2. user_constraints.json</br>

```
{
  "budget": 25,
  "allergy": ["flour"],
  "calorieLimit": 700
}
```
**Requirements**</br>
* Dish Selection: Exactly one dish must be selected from each category: Appetizer, Drink, Main Course, and Dessert.
* Budget Compliance: The total cost of selected dishes must not exceed the specified budget.
* Allergy Consideration: Dishes containing any allergenic ingredient listed in the user constraints must be excluded.
* Calorie Limitation: The total calorie count of the selected dishes must stay within the specified limit.
* Satisfaction Optimization: Among the feasible dish combinations, the one with the highest total satisfaction score should be selected. If there is a tie in satisfaction, the cheaper combination should be chosen.

**Output**</br>
When meal selection hits a snag due to the constraints, the program should respond with a whimsically worded JSON error message. Let's add a dash of creativity to make the messages engaging and a little humorous:
```
{
  "error": "Culinary conundrum! We couldn't whip up a meal that meets all your gastronomic guidelines."
}
```

**For successful selections, the output should be:**</br>
```
{
  "selectedFoods": [
    {"category": "Appetizer", "name": "Bruschetta"},
    {"category": "Drink", "name": "Soda"},
    {"category": "Main Course", "name": "Lasagna"},
    {"category": "Dessert", "name": "Cheesecake"}
  ],
  "totalCost": 20,
  "totalSatisfaction": 24,
  "calories": 690
}
```
**Constraints**</br>
* Choose any programming language you like
* Feel free to create a solution from scratch or use any solution from the Best Meal challenge as a starting point
* You can use any external libraries that you feel will help you
* Feel free to use AI to assist your development - the only exception to this is you CANNOT USE AI TO WRITE A SOLUTION for you
* Maximum Dishes Per Category: Each category can have up to 40 dishes.
* Valid JSON: Input files will be well-formed and valid.
* Calorie Range: Individual dishes will have calories not exceeding 2000.
* Budget Limit: The budget will not exceed $1000.
* Dish Name Uniqueness: Each dish name within a category will be unique.
* Ingredient Specificity: Ingredients will be clearly listed; no unspecified or hidden ingredients.

**Scoring**</br>
* Correctness and Completeness (60 points): Solutions must pass all provided test cases, including edge cases.
* Code Quality and Style (30 points): Well-structured code with clear comments and good variable naming is expected. Summary of your thought process - Executive Summary to give an overview of what you're solutions does is required.
* Performance and Efficiency (10 points): Fast execution times will be rewarded, but solutions must first and foremost be correct and complete.

**Example:**</br>

Inputs:</br>
**menu.json**
```
{
  "foods": [
    {
      "name": "Fried Calamari",
      "cost": 6,
      "satisfaction": 5,
      "category": "Appetizer",
      "ingredients": ["calamari", "flour", "oil"],
      "calories": 300
    },
    {
      "name": "Spinach Dip",
      "cost": 5,
      "satisfaction": 3,
      "category": "Appetizer",
      "ingredients": ["spinach", "cream cheese", "garlic"],
      "calories": 250
    },
    {
      "name": "Bruschetta",
      "cost": 4,
      "satisfaction": 4,
      "category": "Appetizer",
      "ingredients": ["bread", "tomatoes", "basil"],
      "calories": 150
    },
    {
      "name": "Soda",
      "cost": 1,
      "satisfaction": 1,
      "category": "Drink",
      "ingredients": ["water", "sugar", "carbon dioxide"],
      "calories": 150
    },
    {
      "name": "Coffee",
      "cost": 2,
      "satisfaction": 2,
      "category": "Drink",
      "ingredients": ["coffee beans", "water"],
      "calories": 5
    },
    {
      "name": "Beer",
      "cost": 3,
      "satisfaction": 2,
      "category": "Drink",
      "ingredients": ["barley", "hops", "water"],
      "calories": 150
    },
    {
      "name": "Steak",
      "cost": 12,
      "satisfaction": 10,
      "category": "Main Course",
      "ingredients": ["beef", "salt", "pepper"],
      "calories": 800
    },
    {
      "name": "Salmon",
      "cost": 11,
      "satisfaction": 9,
      "category": "Main Course",
      "ingredients": ["salmon", "lemon", "dill"],
      "calories": 500
    },
    {
      "name": "Vegetable Stir Fry",
      "cost": 7,
      "satisfaction": 6,
      "category": "Main Course",
      "ingredients": ["broccoli", "carrots", "peppers", "soy sauce"],
      "calories": 350
    },
    {
      "name": "Cheesecake",
      "cost": 4,
      "satisfaction": 4,
      "category": "Dessert",
      "ingredients": ["cream cheese", "sugar", "eggs", "crust"],
      "calories": 450
    },
    {
      "name": "Apple Pie",
      "cost": 3,
      "satisfaction": 3,
      "category": "Dessert",
      "ingredients": ["apples", "sugar", "flour", "butter"],
      "calories": 300
    },
    {
      "name": "Ice Cream",
      "cost": 2,
      "satisfaction": 2,
      "category": "Dessert",
      "ingredients": ["milk", "sugar", "cream"],
      "calories": 200
    }
  ]
}
```

**user_constraints.json**
```
{
  "budget": 100,
  "allergy": ["basil"],
  "calorieLimit": 1100
}
```

**Output**
```
{
  "selectedFoods": [
    {"category": "Appetizer", "name": "Fried Calamari"},
    {"category": "Drink", "name": "Coffee"},
    {"category": "Main Course", "name": "Salmon"},
    {"category": "Dessert", "name": "Ice Cream"}
  ],
  "totalCost": 21,
  "totalSatisfaction": 18,
  "calories": 1005
}
```
</br>

**Submissions**</br>
:+1:Please have the solution either emailed to me or a PR submitted to GitHub by Friday June 7th, by midnight:+1:
