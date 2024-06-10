
public class Food {
    private String name = new String();
    private int cost;
    private int satisfaction;
    private String category;
    private int calories;
    
    public void setName(String inputName){
        name = inputName;        
    }
    
    public String getName(){
        return name;
    }
    
    public void setCost(int inputCost){
        cost = inputCost;        
    }
    
    public int getCost(){
        return cost;
    }
    
    public void setSatisfaction(int inputSatisfaction){
        satisfaction = inputSatisfaction;        
    }
    
    public int getSatisfaction(){
        return satisfaction;
    }
    
    public void setCategory(String inputCategory){
        category = inputCategory;        
    }
    
    public String getCategory(){
        return category;
    }
    
    public void setCalories(int inputCalories){
        calories = inputCalories;        
    }
    
    public int getCalories(){
        return calories;
    }
    
}
