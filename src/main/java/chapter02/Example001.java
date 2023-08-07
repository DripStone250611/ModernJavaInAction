package chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect : 挑选库存里的水果
 */
public class Example001 {

    public static void main(String[] args){
        List<Apple> apples = new ArrayList<>(
                Arrays.asList(new Apple("green", 30), new Apple("red", 156), new Apple("green", 192), new Apple("red", 23))
        );
        // 1. 筛选绿色苹果
        List<Apple> set1 = filterGreenApples(apples);
        System.out.println(set1);
        // 2. 筛选红色苹果
        List<Apple> set2 = filterAppleByColor(apples, "red");
        System.out.println(set2);
        // 3. 筛选重量大于150g的苹果
        List<Apple> set3 = filterApples1(apples, "", 150, false);
        System.out.println(set3);
        // 4. 筛选绿色的苹果
        List<Apple> set4 = filterApples2(apples, new AppleGreenColorPredicate());
        System.out.println(set4);
        // 5. 使用匿名类
        List<Apple> set5 = filterApples2(apples, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals("green");
            }
        });

        // 6. 使用lambda表达式
        List<Apple> result = filterApples2(apples, (Apple apple)->"red".equals(apple.getColor()));
    }



    /**
     * @Description //TODO 筛选出库存里的绿色的苹果(只能筛选绿色的苹果)
     * @Param inventory 苹果库存列表
     * @return 绿色苹果列表
     **/
    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * @Description //TODO 把颜色作为参数，根据参数的不同可以筛选出不同颜色的苹果
     * @Param inventory 苹果库存列表, color 苹果颜色
     * @return 指定颜色的苹果列表
     **/
    public static List<Apple> filterAppleByColor(List<Apple> inventory, String color){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * @Description //TODO 既可以筛选颜色和又可以筛选重量, 比较笨拙的解决方案，flag为true时筛选颜色，flag为false时筛选重量
     * @Param
     * @return
     **/
    public static List<Apple> filterApples1(List<Apple> inventory, String color, int weight, boolean flag){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)){
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * @Description //TODO 使用参数行为化的策略来实现筛选指定重量和指定颜色苹果的方法
     * @Param
     * @return
     **/
    public static List<Apple> filterApples2(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple : inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

}

interface ApplePredicate{
    boolean test(Apple apple);
}

class AppleHeavyWeightPredicate implements ApplePredicate{
    public boolean test(Apple apple){
        return apple.getWeight() > 150;
    }
}

class AppleGreenColorPredicate implements ApplePredicate{
    public boolean test(Apple apple){
        return "green".equals(apple.getColor());
    }
}
