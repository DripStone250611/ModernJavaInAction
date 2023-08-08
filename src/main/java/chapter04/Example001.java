package chapter04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect :
 */
public class Example001 {

    public static void main(String[] args) {

        List<Dish> menu = new ArrayList<>();

    }

    // Java7 筛选低卡路里，按照卡路里排序，并输出菜肴名
    public static List<String> filterLowCaloricDished(List<Dish> menu){
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for(Dish dish:menu){
            if(dish.getCalories() < 400){
                lowCaloricDishes.add(dish);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>(){

            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes){
            lowCaloricDishesName.add(dish.getName());
        }
        return lowCaloricDishesName;
    }

    public static void filterLowCaloricDished2(List<Dish> menu){
        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
    }
}

