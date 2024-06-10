
public class Meal {
    private Food appetizer = new Food();
    private Food drink = new Food();
    private Food mainCourse = new Food();
    private Food dessert = new Food();
    private int totalSatisfaction = -1;
    private int totalCost = 0;
    private int totalCalories = 0;
    
    public Meal() {
        
    }
    
    public Meal (Food app, Food dr, Food mc, Food des) {
        appetizer = app;
        drink = dr;
        mainCourse = mc;
        dessert = des;
        totalSatisfaction = appetizer.getSatisfaction() + drink.getSatisfaction() + mainCourse.getSatisfaction() + dessert.getSatisfaction();
        totalCost = appetizer.getCost() + drink.getCost() + mainCourse.getCost() + dessert.getCost();
        totalCalories = appetizer.getCalories() + drink.getCalories() + mainCourse.getCalories() + dessert.getCalories();
    }
    
    public Food getAppetizer() {
        return appetizer;
    }
    
    public Food getDrink() {
        return drink;
    }
    
    public Food getMainCourse() {
        return mainCourse;
    }
    
    public Food getDessert() {
        return dessert;
    }
    
    public int getSatisfaction() {
        return totalSatisfaction;
    }
    
    public int getCost() {
        return totalCost;
    }
    
    public int getCalories() {
        return totalCalories;
    }
    
    
    public void setAppetizer(Food app) {
        appetizer = app;
    }
    
    public void setDrink(Food dr) {
        drink = dr;
    }
    
    public void setMainCourse(Food mc) {
        mainCourse = mc;
    }
    
    public void setdessert(Food des) {
        dessert = des;
    }
    
    public void setSatisfaction(int sat) {
        totalSatisfaction = sat;
    }
    
    public void setCost(int cost) {
        totalCost = cost;
    }
    
    
            

}
