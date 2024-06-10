# MEAL PICKER

This app takes a 'menu.json' (or other menu json file) and calculates the most satisfying
meal based on a specified budget.

## Operation
Build the application like so:
```
mvn clean package
```
Run the application like this:
```
java -jar target/mealpicker-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Optional
By default, the application will look for a 'menu.json' and 'user_constraints.json' file in the current directory.
You can override this by specifying the path to another menu file as a command line argument.

Example:
```
java -jar target/mealpicker-1.0-SNAPSHOT-jar-with-dependencies.jar giantMenu.json user_constraints.json
```

# Logic
The menu is parsed into a list of Foods. Each food has a name, cost, satisfaction rating, and category.
The foods are then organized into 4 separate lists:
* Appetizer
* Drink
* Main Course
* Dessert

Each list is sorted by descending satisfaction value.

Once the menu is read in, this filters out food that contain ingredients that the user is allergic to.

The meal calculation is then done by walking through every combination of Appetizer, Drink, Main Course, Dessert and 
discarding the meal if its cost is higher than the budget OR exceeds the calorie limit. The meal selector then returns the 
combination with the highest satisfaction.

A meal tracks foods as indexes into the category lists to avoid having to copy a lot of objects around. 