package chapter06;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.partitioningBy;

/**
 * @author : YINAN
 * @date : 2023/8/10
 * @effect : 获取0-100的所有质数
 */
public class Example002 {

    public static void main(String[] args) {

        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++){
            long start = System.nanoTime();
            //TODO
            Map<Boolean, List<Integer>> primes = IntStream.rangeClosed(2, 1000000).boxed().collect(partitioningBy(
                    candidate -> isPrime1(candidate)
            ));


//            Map<Boolean, List<Integer>> primes2 = IntStream.rangeClosed(2, 1000000).boxed().collect(new PrimeCollector());


            long duration = (System.nanoTime() - start) / 1000000;
            if(duration < fastest) fastest = duration;
        }
        System.out.println(fastest);


    }

    public static boolean isPrime1(int candidate){
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(
                i -> candidate % i == 0
        );
    }


}

class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>>{

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>(){{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(isPrime(acc.get(true), candidate)).add(candidate);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    public static boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int)Math.sqrt((double) candidate);
        return primes.stream()
                .takeWhile(i -> i <= candidateRoot)
                .noneMatch(j -> candidate % j == 0);
    }
}
