package chapter05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author : YINAN
 * @date : 2023/8/8
 * @effect :
 */
public class Exercise002 {


    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        System.out.println(filterTransactionByYear(transactions, 2011));
        List<Trader> traders = Arrays.asList(raoul, mario, alan, brian);
        System.out.println(getWorkedCities(traders));
        System.out.println(filterTraderByCity(traders, "Cambridge"));
        System.out.println(getTraderName(traders));
        System.out.println(isTraderInCity(traders, "Milan"));

        System.out.println(getMaxTransactionValue(transactions));
        System.out.println(getMinTransaction(transactions));


        // 测试特化数据流
        int maxValue = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()
                .orElse(0);
        System.out.println(maxValue);

        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .filter(b -> Math.sqrt(a * a + b* b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a * a + b* b)})
                );
//        List<int[]> pytr = pythagoreanTriples.collect(Collectors.toList());
//        pytr.forEach(item -> {
//            System.out.println(item[0] + ", " + item[1] + ", " + item[2]);
//        });

        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[0] + t[1], t[0] + 2 * t[1]})
                .limit(10)
                .flatMapToInt(Arrays::stream)
                .forEach(System.out::println);



    }


    public static List<Transaction> filterTransactionByYear(List<Transaction> transactions, int year){
        return transactions.stream()
                .filter(t -> t.getYear() == year)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }

    public static List<String> getWorkedCities(List<Trader> traders){
        return traders.stream()
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<Trader> filterTraderByCity(List<Trader> traders, String city){
        return traders.stream()
                .filter(t -> t.getCity().equals(city))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
    }

    public static List<String> getTraderName(List<Trader> traders){
        return traders.stream()
                .sorted(Comparator.comparing(Trader::getName))
                .map(Trader::getName)
                .collect(Collectors.toList());
    }

    public static boolean isTraderInCity(List<Trader> traders, String city){
        return traders.stream()
                .anyMatch(t -> t.getCity().equals(city));
    }


    public static Integer getMaxTransactionValue(List<Transaction> transactions){
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .get();
    }

    public static Transaction getMinTransaction(List<Transaction> transactions){
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .findFirst()
                .get();
    }

}
