package chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : YINAN
 * @date : 2023/8/8
 * @effect :
 */
public class Exercise001 {



    public static void main(String[] args) {

        // 测试takeWhile和dropWhile
        List<Integer> numbers1 = Arrays.asList(1,2,3,3,4,5,6,7);
        List<Integer> numbers2 = Arrays.asList(1,2,3,2,3,4,3,2);

        List<Integer> res1 = numbers1.stream()
                .takeWhile(i -> i < 4)
                .collect(Collectors.toList());
        List<Integer> res2 = numbers2.stream()
                .takeWhile(i -> i < 4)
                .collect(Collectors.toList());
        System.out.println(res1);
        System.out.println(res2);

        List<Integer> res3 = numbers1.stream()
                .dropWhile(i -> i < 4)
                .collect(Collectors.toList());
        System.out.println(res3);
                


    }
}
