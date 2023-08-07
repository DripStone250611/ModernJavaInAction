package chapter04;



public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }


    public String getName() {
        return this.name;
    }

    public int getCalories() {
        return this.calories;
    }

    public boolean getVegetarian(){
        return this.vegetarian;
    }

    public Type getType(){
        return this.type;
    }
    public enum Type{
        MEAT, FISH, OTHER
    }
}
