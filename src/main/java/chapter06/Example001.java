package chapter06;

import chapter04.Dish;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author : YINAN
 * @date : 2023/8/8
 * @effect :
 */
public class Example001 {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
          new Dish("pork", false, 800, Dish.Type.MEAT),
          new Dish("beef", false, 700, Dish.Type.MEAT),
          new Dish("chicken", false, 400, Dish.Type.MEAT),
          new Dish("french fries", true, 530, Dish.Type.OTHER),
          new Dish("rice", true, 350, Dish.Type.OTHER),
          new Dish("season fruit", true, 120, Dish.Type.OTHER),
          new Dish("pizza", true, 550, Dish.Type.OTHER),
          new Dish("prawns", false, 300,Dish.Type.FISH),
          new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        Map<Dish.Type,List<Dish>> groupDishByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.toList()));
        System.out.println(groupDishByType);

        Map<Dish.Type, List<String>> groupDishNameByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toCollection(ArrayList::new))));
        System.out.println(groupDishNameByType);

        Map<Dish.Type, Dish> getMaxCaloriesByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),Optional::get
                )));


    }
}
