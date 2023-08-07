package chapter2;

import java.util.List;

/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect :
 */
public class Exercise001 {
    /** 
     * 编写一个prettyPrintApple方法
     * 接收一个Apple的List
     * 参数化
     * 以多种方式根据苹果生成一个String输出
     * */
    
    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter applePredicate1){
        for(Apple apple : inventory){
            String output = applePredicate1.toString(apple);
            System.out.println(output);
        }
        
    }
}

interface AppleFormatter{
    public String toString(Apple apple);
}

class AppleFancyFormatter implements AppleFormatter{
    public String toString(Apple apple){
        String characteristic = apple.getWeight() > 150 ? "heavy":"light";
        return "A " + characteristic + " " + apple.getColor() + " apple";
    }
}
