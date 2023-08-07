package chapter02;

public class Apple {
    private String color;
    private double weight;

    public Apple(String color, double weight) {
        this.color = color;
        this.weight = weight;
    }


    public Double getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor() {
        this.color = color;
    }

    public void setWeight() {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + this.color + ", " + this.weight + "]";
    }
}
