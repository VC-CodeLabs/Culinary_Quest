import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

    public class PHobbyRestaurant {
        
        private static LinkedList<Food> appetizers = new LinkedList<Food>();
        private static LinkedList<Food> drinks = new LinkedList<Food>();
        private static LinkedList<Food> mainCourses = new LinkedList<Food>();
        private static LinkedList<Food> desserts = new LinkedList<Food>();
    
        //The following 8 are used in the messages at the end;
        private static boolean noStartingApps = true;
        private static boolean noStartingDrinks = true;
        private static boolean noStartingMainCourses = true;
        private static boolean noStartingDesserts = true;
        
        private static int highestAppSat = Integer.MIN_VALUE;
        private static int highestDrinkSat = Integer.MIN_VALUE;
        private static int highestMCSat = Integer.MIN_VALUE;
        private static int highestDesSat = Integer.MIN_VALUE;
    
        public static void main(String[] args) throws FileNotFoundException {
            // TODO Add try catch for when json isn't good;
            
            boolean simple = false;  //trying to future-proof this a bit. If you need a list of all possible meals, change this to false.
                                    //If you just need the highest satisfaction, set this true.
            
            Meal mostSatisfactoryMeal = new Meal();
                    
            Gson gson = new Gson();
            
            try {
                /*** Please insert your Menu in the next line. ***/
                FileReader reader = new FileReader("menu23.json");
                JsonObject jsonMenu = gson.fromJson(reader, JsonObject.class);
                //This bit separates the information into the budget and the food (json) array.                      
                JsonArray foods = jsonMenu.get("foods").getAsJsonArray();
              
                if (foods.size() < 4) { //not enough foods to make a full meal in the first place. Break
                    printSmallRestaurantMessage();
                }
                else if (jsonMenuLacksCategory(foods) == true ) { //The menu lacks at least one category -- don't bother doing the rest.
                    printFoodLackMessage();
                }
                
                else { //more than 4 foods, i.e. proceed
                    /*** Please insert your User Constraints in the next line. ***/
                    FileReader reader2 = new FileReader("user_constraints.json");
                    JsonObject userConstraints = gson.fromJson(reader2, JsonObject.class);
            
                    int budget = userConstraints.get("budget").getAsInt(); 
                    JsonArray allergens = userConstraints.get("allergy").getAsJsonArray();
                    int calorieLimit = userConstraints.get("calorieLimit").getAsInt();
                                        
                    createFoodLists(foods, budget, allergens, calorieLimit); //method below, which creates Food objects and stores them in the correct Linked List (Apps, Drinks, etc.)
            
                    if (!allListsHaveFood()) {//not enough foods in one category that you can afford
                        printCategoryLackBudget();
                    }
                    
                    else {//all lists have at least one food.            
                        if (simple == false) {//This can order by cost
                            mostSatisfactoryMeal = findCostliestMostSatisfactoryMeal(budget);
                            
                        }
                        
                        else {//This prints the first best meal it comes across.
                            mostSatisfactoryMeal = findMostSatisfactoryMeal(budget);            
                        } 
                        
                        printMostSatisfactoryMeal(mostSatisfactoryMeal, budget);
                    }
                }
            }
            catch(Exception e) {
                if (e.getMessage().contains("No such file")){
                    System.out.println(e); 
                    System.out.print("Please check the file path and try again.");
                }
                else {
                    System.out.println("A .json file you have used has an issue. Please either fix the issue or upload a new .json file.");
                    System.out.println(e);
                }
            }
                   
        }
    
        private static Meal findCostliestMostSatisfactoryMeal(int budget) {
            LinkedList<Meal> mealsList = createListOfAllMeals(budget); //This is ordered by Satisfaction.
            Meal leastExpensiveMeal = findLeastExpensiveMeal(mealsList);
            
            return leastExpensiveMeal;

        }

        private static Meal findLeastExpensiveMeal(LinkedList<Meal> mealsList) {
            Iterator<Meal> mealIter = mealsList.iterator();
            Meal bestMeal = mealIter.next(); //keeps First Meal and moves Iterator to next. First Meal has the highest Satisfaction, but may not have lowest cost.
            int highestSatisfaction = mealsList.getFirst().getSatisfaction(); //This should never change and will keep you from going through the whole list
            int lowestCostSoFar = mealsList.getFirst().getCost();
            
            while (mealIter.hasNext()) {
                Meal thisMeal = mealIter.next();
                
                if (thisMeal.getSatisfaction() == highestSatisfaction) {//else it is lower, and this should simply break out of the while loop.
                    if (thisMeal.getCost() < lowestCostSoFar) {
                        bestMeal = thisMeal;
                        lowestCostSoFar = thisMeal.getCost();
                    }                    
                }
            }
            
            return bestMeal;
            
        }

        //Below this are all the methods to run this.
    
        private static boolean jsonMenuLacksCategory(JsonArray foods) { //This checks to make sure each Category has at least one food.
                                                                        //It also checks to see what the highest possible satisfaction per category is. This is for the message at the end.
            
            for (int i = 0; i < foods.size(); i++) {
                JsonElement foodJsonElement = foods.get(i); //gets food at element i
                JsonObject foodJsonObject = foodJsonElement.getAsJsonObject();
                
                if (foodJsonObject.get("category").getAsString().equalsIgnoreCase("Appetizer")) {
                    noStartingApps = false;

                    if (foodJsonObject.get("satisfaction").getAsInt() > highestAppSat) {
                        highestAppSat = foodJsonObject.get("satisfaction").getAsInt();
                    }
                }
                else if (foodJsonObject.get("category").getAsString().equalsIgnoreCase("Drink")) {
                    noStartingDrinks = false;
                    
                    if (foodJsonObject.get("satisfaction").getAsInt() > highestDrinkSat) {
                        highestDrinkSat = foodJsonObject.get("satisfaction").getAsInt();
                    }
                } 
                else if (foodJsonObject.get("category").getAsString().equalsIgnoreCase("Main Course")) {
                    noStartingMainCourses = false;
                    
                    if (foodJsonObject.get("satisfaction").getAsInt() > highestMCSat) {
                        highestMCSat = foodJsonObject.get("satisfaction").getAsInt();
                    }
                }
                else if (foodJsonObject.get("category").getAsString().equalsIgnoreCase("Dessert")) {
                    noStartingDesserts = false;
                    
                    if (foodJsonObject.get("satisfaction").getAsInt() > highestDesSat) {
                        highestDesSat = foodJsonObject.get("satisfaction").getAsInt();
                    }
                }
                /* Needed to take this out, as the counting of highest satisfaction requires going through everything.
                if (noStartingApps == false && noStartingDrinks == false && noStartingMainCourses == false && noStartingDesserts == false) {
                    return false;
                    //in other words, the menu has all four categories, and this breaks out of the loop early if it detects one of each.
                } */
            }
            if (noStartingApps || noStartingDrinks || noStartingMainCourses || noStartingDesserts) {
                return true;  //if any of them is lacking one, it returns true.
            }
            else {
                return false; //shouldn't have to get here, as it should have already broken the loop.
            }
        }
    
    
    
        private static void createFoodLists(JsonArray foods, int budget, JsonArray allergens, int calorieLimit) {
            LinkedList<Food> foodList = new LinkedList<Food>();
            
            for (int i = 0; i < foods.size(); i++) {
                JsonElement foodJsonElement = foods.get(i); //gets food at element i
                JsonObject thisFood = foodJsonElement.getAsJsonObject();
                
                //this if statement creates a food iff the cost is lower than the budget, the calories are lower than the limit, and there are no allergens in the food.
                if (thisFood.get("cost").getAsInt() < budget && thisFood.get("calories").getAsInt() < calorieLimit && containsNoAllergens(thisFood, allergens)) { 
                    Food food = new Food();
    
                    food.setName(thisFood.get("name").getAsString());
                    food.setCost(thisFood.get("cost").getAsInt());
                    food.setSatisfaction(thisFood.get("satisfaction").getAsInt());
                    food.setCategory(thisFood.get("category").getAsString());
                    food.setCalories(thisFood.get("calories").getAsInt());
                    /* Please note that, although there are two more attributes, we have already used them above.
                     * For now, there is no reason to include these into the object, so we will not waste time or space doing this.
                     */
                    
                    //This adds the food to its correct food list (e.g. Drinks, etc.)
                    addToCorrectFoodList(food);
                }
            }
        }
    
        private static boolean containsNoAllergens(JsonObject thisFood, JsonArray allergens) {
            if (allergens.isEmpty()) {
                return true; //i.e. there were no allergens in the first place;
            }
            else {//this compares the allergens to the ingredients of this Food.
                JsonArray ingredients = thisFood.get("ingredients").getAsJsonArray();
                for (int ingredient = 0; ingredient < ingredients.size(); ingredient++) {
                    String thisIngredient = ingredients.get(ingredient).getAsString();
                    for (int allergen = 0; allergen < allergens.size(); allergen++) {
                        String thisAllergen = allergens.get(allergen).getAsString();
                        if (thisIngredient.equalsIgnoreCase(thisAllergen)) { // i.e. contains an allergen
                            return false;
                        }
                    }
                }//if you've gotten this far, there were no interferences.
                return true;
            }
        }

        private static void addToCorrectFoodList(Food food) {
            if (food.getCategory().equalsIgnoreCase("Appetizer")) {
                appetizers.add(food);
            }
            else if (food.getCategory().equalsIgnoreCase("Drink")) {
                drinks.add(food);
            }
            else if (food.getCategory().equalsIgnoreCase("Main Course")) {
                mainCourses.add(food);
            }
            else if (food.getCategory().equalsIgnoreCase("Dessert")) {
                desserts.add(food);
            }
            //if not any of these, it wasn't a food.
        }
        
      /*  This is a more complex way, but it keeps track of all meals should you need this.
       *  This method keeps all possible meals, and it orders them by Satisfaction.
       */
        private static LinkedList<Meal> createListOfAllMeals(int budget) { 
            LinkedList<Meal> mealsList = new LinkedList<Meal>();
                
            Iterator<Food> appIter = appetizers.iterator();
            
            while (appIter.hasNext()) {
                Food thisApp = appIter.next();
                Iterator<Food> drinksIter = drinks.iterator();
                
                while (drinksIter.hasNext()) {
                    Food thisDrink = drinksIter.next();
                    Iterator<Food> mcIter = mainCourses.iterator();
                    
                    while (mcIter.hasNext()) {
                        Food thisMC = mcIter.next();
                        Iterator<Food> dessertsIter = desserts.iterator();
                        
                        while (dessertsIter.hasNext()) {
                            Food thisDessert = dessertsIter.next();
                            
                            int totalCost = thisApp.getCost() + thisDrink.getCost() + thisMC.getCost() + thisDessert.getCost();  
                            
                            if (totalCost <= budget) {
                                Meal thisMeal = new Meal(thisApp, thisDrink, thisMC, thisDessert);
                                mealsList.add(findSatisfactionIndex(mealsList, thisMeal), thisMeal);                                                                
                            }
                        }
                    }
                }
            }
            
            return mealsList;
        }
        
        /*
         * This method returns the index of where the meal should be when ordered by Satisfaction.
         */
        private static int findSatisfactionIndex(LinkedList<Meal> mealsList, Meal inputMeal) {
            int index = 0;
            Iterator<Meal> mealsListIterator = mealsList.iterator();

            while (mealsListIterator.hasNext()) {
                Meal thisMeal = mealsListIterator.next();
                
                if (inputMeal.getSatisfaction() >= thisMeal.getSatisfaction()) {
                    return index;                    
                }
                else {
                    index++;
                }
            }    

            return index;
        }

        private static Meal findMostSatisfactoryMeal(int budget) { //this is the simple way to do it
            Meal mostSatisfactoryMeal = new Meal();
            int highestSatisfaction = -1;
            
            Iterator<Food> appIter = appetizers.iterator();
            
            while (appIter.hasNext()) {
                Food thisApp = appIter.next();
                Iterator<Food> drinksIter = drinks.iterator();
                
                while (drinksIter.hasNext()) {
                    Food thisDrink = drinksIter.next();
                    Iterator<Food> mcIter = mainCourses.iterator();
                    
                    while (mcIter.hasNext()) {
                        Food thisMC = mcIter.next();
                        Iterator<Food> dessertsIter = desserts.iterator();
                        
                        while (dessertsIter.hasNext()) {
                            Food thisDessert = dessertsIter.next();
                            
                            int totalCost = thisApp.getCost() + thisDrink.getCost() + thisMC.getCost() + thisDessert.getCost();  
                            
                            if (totalCost <= budget) { //exclude a meal if it is out of the budget
                                int thisSatisfaction = thisApp.getSatisfaction() + thisDrink.getSatisfaction() + thisMC.getSatisfaction() + thisDessert.getSatisfaction();
                                if (thisSatisfaction > highestSatisfaction) {
                                    mostSatisfactoryMeal = new Meal(thisApp, thisDrink, thisMC, thisDessert);
                                    highestSatisfaction = thisSatisfaction;
                                }                          
                            }
                        }
                    }
                }
            }
            return mostSatisfactoryMeal;        
        }
        
        //Below this are all the messages that could show up.
        
        private static void printCategoryLackBudget() {
            System.out.print("We are very sorry. Unfortunately, there are not enough foods at this restaurant that satisfy your budget and dietary restrictions.");
        }
        
        
        private static void printFoodLackMessage() {
            System.out.println("Unfortunately, this restaurant does not serve any foods in the following categories to make a meal.");
            if (noStartingApps == true) {
                System.out.println("  • Appetizers");
            }
            if (noStartingDrinks == true) {
                System.out.println("  • Drinks");
            }
            if (noStartingMainCourses == true) {
                System.out.println("  • Main Courses");
            }
            if (noStartingDesserts == true) {
                System.out.println("  • Desserts");
            }
            System.out.println("Can you believe this place? Counfounded cheek. What a dump.");
            System.out.print("I warned them back in '92 that they needed more ");
            if (noStartingApps == true) {
                System.out.print("appetizers");
            }
            else if (noStartingDrinks == true) {
                System.out.print("drinks");
            }
            else if (noStartingMainCourses == true) {
                System.out.print("main courses");
            }
            else if (noStartingDesserts == true) {
                System.out.print("desserts");
            }
            System.out.print(".");
            
        }
    
        private static boolean allListsHaveFood() {
            if (appetizers.size() == 0 || drinks.size() == 0 || mainCourses.size() == 0 || desserts.size() == 0) {
                return false;
            }
            else {
                return true;
            }
        }
    
        private static void printSmallRestaurantMessage() {
            System.out.print("There are not enough foods in this restaurant. What a small restaurant. What is this, a restaurant for ants?!");
            
        }
    
        private static void printMostSatisfactoryMeal(Meal mostSatisfactoryMeal, int budget) {
    
            if (mostSatisfactoryMeal.getSatisfaction() >= 0) { // This found a meal
                int highestPossibleSatisfaction = highestAppSat + highestDrinkSat + highestMCSat + highestDesSat;
                
                System.out.print("The meal from which you will derive the most satisfaction (of " + mostSatisfactoryMeal.getSatisfaction() + " out of a possible " + highestPossibleSatisfaction + ") for the lowest cost is the following: \n" + 
                                 "To start with, you'd like to eat a nice " + mostSatisfactoryMeal.getAppetizer().getName() + ".\n" +
                                 "Paired with this, you will be drinking some lovely " + mostSatisfactoryMeal.getDrink().getName() +".\n" + 
                                 "For the main course, you will be feasting upon scrumptious " + mostSatisfactoryMeal.getMainCourse().getName() + ".\n" +
                                 "And for dessert, why not dine upon a lovely bit of " + mostSatisfactoryMeal.getDessert().getName() + "?\n" + 
                                 "All this, plus the lovely dining ambiance, for the low cost of $" + mostSatisfactoryMeal.getCost() );
                if (budget - mostSatisfactoryMeal.getCost() > mostSatisfactoryMeal.getCost() * .2) {
                    System.out.print(".00, which is well within your budget of $" + budget +  ".00. (Leave your server a nice tip of $" );
                    System.out.printf("%.2f", (mostSatisfactoryMeal.getCost() * .2)); 
                    System.out.print(".) \n");
                }
                else {
                    System.out.print(".00, which is exactly your budget of $" + budget +  ".00! ... meaning you're leaving no tip..."); 
                }
                System.out.println("\nIf you prefer all of this as a .json, here it is:");
                System.out.println("{\n"
                        + "  \"selectedFoods\": [\n"
                        + "    {\"category\": \"Appetizer\", \"name\": \"" + mostSatisfactoryMeal.getAppetizer().getName() +"\"},\n"
                        + "    {\"category\": \"Drink\", \"name\": \"" + mostSatisfactoryMeal.getDrink().getName() + "\"},\n"
                        + "    {\"category\": \"Main Course\", \"name\": \"" + mostSatisfactoryMeal.getMainCourse().getName() + "\"},\n"
                        + "    {\"category\": \"Dessert\", \"name\": \""  + mostSatisfactoryMeal.getDessert().getName() + "\"}\n"
                        + "  ],\n"
                        + "  \"totalCost\": " + mostSatisfactoryMeal.getCost() +  ",\n"
                        + "  \"totalSatisfaction\": " + mostSatisfactoryMeal.getSatisfaction() +  ",\n"
                        + "  \"calories\": " + mostSatisfactoryMeal.getCalories() + "\n"
                        + "}");
            } 
            else { //No meal found.
                System.out.print("Unfortunately, no meals could be found that would satisfy you. \n" + 
                                 "This is either because you are a very picky eater, or because you have \n" +
                                  "picked some very expensive items, or because you are an impecunious party. \n");
            }
        }
    }
