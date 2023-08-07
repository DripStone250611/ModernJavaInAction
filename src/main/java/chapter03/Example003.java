package chapter03;

import chapter02.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect : 给Apple List进行排序
 */
public class Example003 {


    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>(
                Arrays.asList(new Apple("green", 30), new Apple("red", 156), new Apple("green", 192), new Apple("red", 23))
        );
        // 1、使用Comparator的显式实现类
        apples.sort(new AppleComparator());
        System.out.println(apples);
        // 2、使用匿名类
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        // 3、使用Lambda表达式
        apples.sort((Apple o1, Apple o2) -> o1.getWeight().compareTo(o2.getWeight()));
        // 4、使用方法引用
        apples.sort(Comparator.comparing(Apple::getWeight));
    }


}

class AppleComparator implements Comparator<Apple> {

    @Override
    public int compare(Apple apple1, Apple apple2) {
        return apple1.getWeight().compareTo(apple2.getWeight());
    }
}
