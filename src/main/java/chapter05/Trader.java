package chapter05;

/**
 * @author : YINAN
 * @date : 2023/8/8
 * @effect :
 */
public class Trader {

    private final String name;
    private final String city;

    public Trader(String n, String c){
        this.name = n;
        this.city = c;
    }
    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }

    public String toString(){
        return "Trader:" + this.name + " in " + this.city;
    }
}
