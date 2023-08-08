package chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        String[] arrayOfWords = {"GoodBye", "Hello"};
        Stream<String> streamOfWords = Arrays.stream(arrayOfWords);
        List<String> uniqueCharactors = streamOfWords.map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueCharactors);


        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(4, 5);
        List<int[]> pairs = number1.stream()
                .flatMap(i -> number2.stream().map(j -> new int[]{i, j}))
                .filter(item -> (item[0] + item[1]) % 3 == 0)
                .collect(Collectors.toList());
        System.out.println(pairs);

        Boolean res = numbers1.stream().anyMatch(i -> i == 3);
        System.out.println(res);

        Optional<Integer> equalTwoElement = number1.stream().filter(s -> s == 5).findAny();
        System.out.println(equalTwoElement.isEmpty());

    }
}
