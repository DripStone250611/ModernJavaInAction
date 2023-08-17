# Modern Java In Action

## 第 2 章 通过行为参数化传递代码

```
# 问题引入
要求编写一个程序查找库存中所有的绿色苹果，过了一段时间需求变更为查找库存中颜色是绿色且重量超过150g的苹果。
为了应对这种不断变化的需求，将工作量降到最少，就可以通过行为参数化的编码方式解决。
```

**行为参数化**

​		行为参数化就是可以帮助你处理频繁变更的需求的一种软件开发模式。

```java
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

class Apple{
    private String color;
    private double weight;

    public Apple(String color, double weight){
        this.color = color;
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }
    public String getColor(){
        return color;
    }

    public void setColor(){
        this.color = color;
    }

    public void setWeight(){
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "[" + this.color + ", " + this.weight + "]";
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
```

## 第 3 章 Lambda表达式

**lambda表达式**

​	可以把lambda表达式理解为**简洁**地表示**可传递**地**匿名函数**地一种方式：它没有名称，但它有参数列表、函数主体、返回类型。

**java8中有效的Lambda表达式**

```java
(String s) -> s.length()				// 具有一个String类型的参数并返回一个int。Lambda表达式没有return语句，因为已经隐含了return
(Apple a) -> a.getWeight() > 150		// 具有一个Apple类型的参数并返回一个boolean
(int x, int y) -> {						// 具有两个int类型的参数，而没有任何返回参数
    System.out.println("Result:");
    System.out.println(x + y);
}
() -> 42								// 没有任何参数，返回一个int
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())	//具有两个Apple类型参数，返回一个int
```

**可以在函数式接口上使用Lambda表达式**

​	Lambda表达式允许你直接以内联的形式为函数式接口的抽象方法提供实现，并把整个表达式作为函数式接口的实例。

```java
Runnable r1 = () -> System.out.println("Hello World 1");

Runnable r2 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello World 2");
    }
};

public static void process(Runnable r){
    r.run();
}
process(r1);
process(r2);
process(() -> System.out.println("Hello World 3"));
```

**函数式接口**

​	函数式接口就是只定义一个抽象方法的接口。

**函数描述符**

​	函数式接口的抽象方法的签名就是Lambda表达式的签名。我们把这个抽象方法的签名就叫做函数描述符。

​	Java8中的常用函数式接口

|      函数式接口       |     函数描述符     |                         原始类型特化                         |
| :-------------------: | :----------------: | :----------------------------------------------------------: |
|    `Predicate<T>`     |   `T -> boolean`   |       `IntPredicate`,`LongPredicate`,`DoublePredicate`       |
|     `Consumer<T>`     |    `T -> void`     |        `IntConsumer`,`LongConsumer`,`DoubleConsumer`         |
|    `Function<T,R>`    |      `T -> R`      | `IntFuction<R>`,`IntToDoubleFuction`,`IntToLongFunction`,`LongFunction<R>`,<br/>`LongToDoubleFunction`,`LongToIntFunction`,`DoubleFuction<R>`,`ToLongFunction<T>`,<br/>`ToIntFuction<T>`,`ToDoubleFunction<T>` |
|     `Supplier<T>`     |     `() -> T`      | `BooleanSupplier`,`IntSupplier`,`LongSupplier`,`DoubleSupplier` |
|  `UnaryOperator<T>`   |      `T -> T`      | `IntUnaryOperator`, `LongUnaryOperator`,`DoubleUnaryOperator` |
|  `BinaryOperator<T>`  |     `(T,T)->T`     | `IntBinaryOperator`,`LongBinaryOperator`,`DoubleBinaryOperator` |
|  `BiPredicate<L, R>`  | `<L,R> -> boolean` |                                                              |
|  `BiConsumer<T, U>`   |  `(T, U) -> void`  | `ObjIntConsumer<T>`,`ObjLongConsumer<T>`,`ObjDoubleConsumer<T>` |
| `BiFunction<T, U, R>` |   `(T, U) -> R`    | `ToIntBiFunction<T, U>`, `ToLongBiFunction<T, U>`, `ToDoubleBiFunction<T, U>` |

**方法引用**

​	方法引用可以看作是某些lambda的快捷写法。方法引用让你可以重复使用现有的方法定义，并像lambda一样传递他们。方法引用就是让你根据已有的方法实现来创建Lambda表达式。

```java
// 不使用方法引用
inventory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
// 使用方法引用
inventory.sort(comparing(Apple::getWeight));
```

​	方法引用主要有三类：

- 指向**静态方法**的方法引用（如Integer的`parseInt`方法，写作 `Integer::parseInt`）
- 指向**任意类型实例方法**的方法引用（例如String的length方法，写作`String::length`）
- 指向**现有对象的实例方法**的方法引用（例如有一个expensiveTransaction用于存放Transaction类型的对象，就可以写作`expensiveTransaction::getValue`)

**构造函数引用**

​	对于一个现有构造函数，可以利用它的名字和关键字new来创建它的一个引用：`ClassName::new`

```java
Supplier<Apple> c1 = Apple::new;
Apple a1 = c1.get();

Supplier<Apple> c1 = () -> new Apple();
Apple a1 = c1.get();

Function<Integer, Apple> c2 = Apple::new;
Apple a2 = c2.apply(110);

Function<Integer, Apple> c2 = (weight) -> new Apple(weight);
Apple a2 = c2.apply(110);
```

**复合lambda表达式的有用方法**

​	**1、比较器复合**

```java
// 1、逆序
inventory.sort(comparing(Apple::getWeight).reversed());
// 2、比较器链
inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getCountry));
```

​	**2、谓词复合**

```java
// 谓词接口包括三个方法： negate、and和or
Predicate<Apple> notRedApple = redApple.negate();
// 从左向右确认优先级的
Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 150).or(apple -> GREEN.equals.getColor());
```

​	**3、函数复合**

```java
// Function接口的andThen和compose两个默认方法都会返回Function的一个实例
Function<Integer, Integer> f = x -> x + 1;
Function<Integer, Integer> g = x -> x * 2;
Function<Integer, Integer> h = f.andThen(g); //数学上会写作g(f(x))

Function<Integer, Integer> h = f.compose(g); //数学上会写作f
```



## 第 4 章  引入流



Java8中的Stream API可以让你写出这样的代码：

- 声明性-----更简洁、更易读
- 可复合-----更灵活
- 可并行-----性能更好

**流的定义**

​	从支持**数据处理操作**的**源**生成的**元素序列**。

**流的两个重要特点**

- 流水线：很多流操作本身会返回一个流，这样多个操作就可以连接起来，构成一个很大的流水线。
- 内部迭代：与集合使用迭代器进行显示迭代不同，流的迭代操作是在后台进行的。

**有序列表生成的流会保持原有顺序**

**流只能遍历一次**

​	和迭代器类似，流只能遍历一次。遍历完之后，我们就说这个流已经被消费掉了。

**流操作有两大类**

- 中间操作：中间操作会返回另一个流
- 终端操作：终端操作会从流的流水线上生成结果，其结果是任何不是流的值。

|   操作   | 类型 |  返回类型   |     操作参数     |   函数描述符   |
| :------: | :--: | :---------: | :--------------: | :------------: |
|  filter  | 中间 | `Stream<T>` |  `Predicate<T>`  | `T -> boolean` |
|   map    | 中间 | `Stream<T>` | `Function<T, R>` |    `T -> R`    |
|  limit   | 中间 | `Stream<T>` |                  |                |
|  sorted  | 中间 | `Stream<T>` | `Comparator<T>`  | `(T, T)->int`  |
| distinct | 中间 | `Stream<T>` |                  |                |

| 操作    | 类型 | 返回类型    | 目的                               |
| ------- | ---- | ----------- | ---------------------------------- |
| forEach | 终端 | void        | 消费流中的每个元素并对其应用lambda |
| count   | 终端 | long        | 返回流中元素的个数                 |
| collect | 终端 | （generic） | 把流规约成一个集合，比如List，Map  |

## 第 5 章 使用流

**本章要点**

- 筛选、切片和映射
- 查找、匹配和规约
- 使用数值范围等数值流
- 从多个源创建流
- 无限流

**筛选、切片和映射**

```java
List<Integer> numbers = Arrays.asList(1,2,3,1,3,3,2);
// 筛选
numbers.stream()
  .filter(i -> i % 2 == 0)
  .distinct()
  .forEach(System.out::println);
// 切片
// takeWhile会在遇到第一个不符合要求的元素时停止处理
List<Integer> lessThreeNumbers = numbers.stream().takeWhile(i -> i < 3).collect(toList());
// dropWhile会丢弃满足条件的元素，在遇到第一个不满足元素后返回剩余数据流
List<Integer> greaterOrEqualThreeNumbers = numbers.stream().dropWhile(i -> i < 3).collect(toList());
// 截短流
List<Integer> firstThreeNumbers = numbers.stream().limit(3).collect(toList());
// 跳过元素
List<Integer> skipNumbers = numbers.stream()
  .skip(3)
  .collect(toList());

//flatMap
List<String> uniqueCharacters = words.stream()
  .map(word -> word.split(""))
  .flatMap(Arrays::stream)
  .distinct()
  .collect(toList());
```

**查找和匹配**

```java
// 检查谓词是否至少匹配一个元素
boolean res = numbers1.stream().anyMatch(i -> i == 3);
// 检查谓词是否匹配所有元素
boolean isHealthy = numbers1.stream().allMatch(i -> i > 2);
// 确保数据流中没有任何元素与给定的谓词匹配
boolean noHealthy = numbers1.stream().noneMatch(i -> i > 2);

// 查找
// findAny方法将返回当前流中的任意元素
Optional<Integer> res = numbers1.stream().findAny().ifPresent(i -> System.out.println(i));
// findFirst方法查找第一个元素
```

**归约**

​	归约操作就是将流归约成一个值。

```java
int sum = numbers.stream().reduce(0, (a, b) -> a + b); //reduce 接收两个参数，一个初始值，这里是0，一个BinaryOperator<T>
// reduce还有一个重载的变体，它不接收初始值，但是会返回一个Optional对象。

Optional<Integer> max = numbers.stream().reduce(Integer::max);
```

**数值流和数值范围**

​	Java8中引入了三个原始类型特化流接口来解决装箱拆箱的损耗问题：`IntStream`、`DoubleStream`、`LongStream`，分别将流中的元素特化为int、long和double,从而避免了暗含的装箱成本。

- 将流转换为特化版本的常用方法是mapToInt、mapToDouble、mapToLong。

```java
int calories = menu.stream().mapToInt(Dish::getCalories).sum();
```

- 要把原始流转换为一般流，可以使用boxed方法。

```java
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> stream = inStream.boxed();
```

- Optional也有一个Optional原始类型特化版本：OptionalInt，OptionalDouble和OptionalLong。

```java
OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
int max = maxCalories.orElse(1);
```

​	Java8中引入了两个可以用于IntStream和LongStream的静态方法，帮助生成数值范围：range和rangeClosed, range不包含结束值。

```java
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);
```

**构建流**

- 由值构建流，使用静态方法Stream.of

```java
Stream<String> stream = Stream.of("Modern ", "Java ", "In ", "Action");
```

- 由可空对象创建流（Java 9提供）

```java
String homeValue = System.getProperty("home");
Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(value);
//以上代码在Java 9中可改写成
Stream<String> homeValueStream = Stream.ofNullable(System.getProperty("home"));

//搭配flatMap处理由可空对象构成的流
Stream<String> values = Stream.of("config", "home", "user")
    .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
```

- 由数组创建流

```java
int[] numbers = {2,3,4,5,6};
int sum = Arrays.stream(numbers).sum();
```

- 由文件生成流

```java
long uniqueWords = 0;
try(Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())){
    uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
        .distinct()
        .count();
}
catch(IOException e){
    
}
```

-  从函数生成流

```java
// Stream API提供了两个静态方法从函数中生成流 Stream.iterate和Stream.generate
Stream.iterate(0, n -> n + 2)
    .limit(10)
    .forEach(System.out::println);

Stream.generate(Math::random)
    .forEach(System.out::println);
```



## 第 6 章 用流收集数据

**本章要点**

- 用Collectors类创建和使用收集器
- 将数据流归约成一个值
- 汇总：归约的特殊情况
- 数据分组和分区
- 开发你的自定义收集器

```java
// 将交易列表按照货币种类分组
public static Map<Currency, List<Transaction>> groupTransactionByCurrency(List<Transaction> transactions){
    Map<Currency, List<Transaction>> transactionByCurrency = new HashMap<>();
    for(Transaction transaction : transactions){
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionsForCurrency = transactionByCurrency.get(currency);
        if(transactionsForCurrency == null){
            transactionsForCurrency = new ArrayList<>();
            transactionByCurrency.put(currency, transactionsForCurrency);
        }
        transactionsForCurrency.add(transaction);
    }
}
// 使用流简化代码
public static Map<Currency, List<Transaction>> groupTransactionByCurrency(List<Transaction> transactions){
	return transactions.stream()
        .collect(groupingBy(Transaction::getCurrency));
}
```

**预定义收集器**

​	Collector实用类中提供了很多静态工厂方法，它们主要提供了三大功能：

- 将流元素归约和汇总为一个值
- 元素分组
- 元素分区

**归约和汇总**

```java
// Collectors.maxBy 和 Collectors.minBy 来计算流中的最大值或最小值
Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
Optional<Dish> mostCaloriesDish = menu.stream().collect(maxBy(dishCaloriesComparator));
// Collectors.summingInt
int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));

// summarizingInt收集器会把所有这些信息收集到IntSummarySatatistics类里，它提供了方便的取值（getter）方法访问结果。
IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
// 相应的summarizingLong和summarizingDouble工厂方法有相关的LongSummaryStatistics和DoubleSummaryStatistics类型

//joining工厂方法返回的收集器会把对流中的每一个对象应用toString方法得到的所有字符串连接成一个字符串。
String shortMenu = menu.stream().map(Dish::getName).collect(joining()); //join内部使用了StringBuilder来把生成的字符串追加起来。
// joining工厂方法有一个重载版本可以接受元素之间的分界符，这样就可以得到一个逗号分割的菜肴名称列表。
String shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
```

**元素分组**

```java
public enum CaloricLevel{DIET, NORMAL, FAT}
Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect()
  .groupingBy(dish -> {
    if(dish.getCaloric() <= 400){
      return CaloricLevel.DIET;
    }else if(dish.getCaloric() <= 700){
      return CaloricLevel.NORMAL;
    }else{
      return CaloricLevel.FAT;
    }
  })
  

Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
  .filter(dish -> dish.getCaloric > 500)
  .collect(groupingBy(Dish::getType));
// 上面代码可能存在键值消失
Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream()
  .collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));

Map<String, List<String>> dishTags = new HashMap<>();
dishTags.put("pork", asList("greasy", "salty"));
Map<Dish.Type, Set<String>> dishNameByType = menu.stream()
    .collect(groupingBy(Dish::getType, flatMapping(dish -> 
                                                  dishTags.get( dish.getName() ).stream(), toSet())));

// 使用toCollection可以对类型进行更多的控制
Map<Dish.Type, Set<String>> dishNameByType = menu.stream()
    .collect(groupingBy(Dish::getType, flatMapping(dish ->
                                                  dishTags.get( dish.getName() ). stream(), toCollection(HashSet::new))));

// 多级分组，可以把一个内层的groupingBy传递给外层groupingBy
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishByTypeCaloricLevel = menu.stream()
    .collect(groupingBy(Dish::getType,groupingBy(dish ->{
        if(dish.getCaloric <= 400){
            return CaloricLevel.DIET;
        }else if(dish.getCaloric() <= 700)
            return CaloricLevel.NORMAL;
        else
            return CaloricLevel.FAT;
    })))

// groupingBy里的第二个参数，可以传其他的collector
Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
    .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
// 可以使用Collectors.collectingAndThen,将收集器的结果转换为另一种类型
Map<Dish.Type, Dish> mostCaloricByType = menu.stream()
    .collect(groupingBy(Dish::getType,  collectingAndThen(maxBy(comparingInt(Dish::getCalories)),Optional::get)));
```

**分区**

​	分区是分组的特殊情况，分区函数返回一个布尔值，分区得到的分组Map的键值是boolean，最多可分成两组。

​	优势：分区的好处在于保留了分区函数返回true或false的两套流元素列表。

```java
// 返回一个二级Map
Map<Boolean, Map<Dish.Type, List<Dish>>> vegeratianDishesByType = menu.stream()
    .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
```

Collectors类的静态工厂方法

```java
//	toList			List<T>					把流中的项目收集到一个List中
List<Dish> dishes = menu.stream().collect(toList());
//	toSet			Set<T>					把流中所有项目收集到一个Set中
Set<Dish> dishes = menu.stream().collect(toSet());
//	toCollection	Collection<T>			把流中的项目收集到给定的供应源创建的集合
Collection<Dish> dishes = menu.stream().collect(toCollection(ArrayList::new))；
//	counting		Long					计算流中元素的个数
long howManyDishes = menu.stream().collect(counting());
//	summingInt		Integer					计算流中项目整数元素的和
int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
//	averagingInt	Double					计算流中项目的平均值
double avgCalories = menu.stream().collect(averagingInt(Dish:;getCalories));
//	summarizingInt	IntSummaryStatistics	收集关于流中项目Integer属性的统计值：最大、最小、总和、平均值
IntSummaryStatistics menuStatistics = menuStream.collect(summarizingInt(Dish::getCalories));
//	joining			String					连接对流中每个项目调用toString方法所生成的字符串
String shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
//	maxBy			Optional<T>				一个包裹了流中按照给定比较器选出的最大元素的Optional
Optional<Dish> fattest = menu.stream().collect(maxBy(comparingInt(Dish::getCalories)));
//	minBy			Optional<T>				一个包裹流中按照给定比较器选出的最小元素的Optional
Optional<Dish> lightest = menu.stream().collect(minBy(comparingInt(Dish::getCalories)));
//	reducing		归约操作产生的类型		  从一个作为累加器的初始值开始，利用BinaryOperator与流中的元素逐个结合，将流归为单一值
int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
//	collectingAndThen 转换函数返回的类型		  包裹另一个收集器，对其结果应用转换函数
int	howManyDishes = menu.stream().collect(collectingAndThen(toList(), List::size));
//	groupingBy		Map<K, List<T>>			根据项目的一个属性的值对流中的项目作分组，并将属性值作为结果Map的键

// partitioningBy	Map<Boolean, List<T>>	根据对流中每个项目应用Predicate的结果来对项目分区
```



**收集器接口**

```java
public interface Collector<T, A, R> {
    Supplier<A>	supplier();							// 创建一个空的累加器实例，供数据收集过程使用
    BiConsumer<A, T> accumulator();					// 将元素添加到结果容器
    Function<A, R> finisher();						// 对结果容器应用最终转换
    BinaryOperator<A> combiner();					// 合并两个结果容器（对流并行处理时会用到）
    Set<Characteristics> characteristics();			// 返回一个不可变的characteristics集合，定义收集器的行为
}
```

- T是流中要收集项目的泛型
- A是累加器的类型，累加器是在收集过程中用于累积部分结果的对象
- R是收集操作得到的对象的类型

```java
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    public Supplier<List<T>> supplier(){
        return ArrayList::new;
    }
    
    public BiConsumer<List<T>, T> accumulator(){
        return List::add;
    }
    public Function<List<T>, List<T>> finisher(){
        return Function.identity();
    }
    public BinaryOperator<List<T>> combiner(){
        return (list1, list2) ->{
            list1.addAll(list2);
            return list1;
        }
    }
    public Set<Characteristics> characteristics(){
        return Collections.unmodifiableSet(EnumSet.of(
        IDENTIY_FINISH, CONCURRENT));
    }
}
```

## 第 7 章 并行数据处理与性能

```java
// 对顺序流调用parallel方法，流本身不会有任何实际的变化，它仅仅是在内部设置了一个boolean标志
// 可以把sequential和parallel组合起来使用
stream.parallel().filter().sequential().map(...).parallel().reduce();
```

## 第 8 章 Collection API的增强功能

**本章内容**

- 如何使用集合工厂
- 学习使用新的惯用模式处理List和Set
- 学习通过惯用模式处理Map

**集合工厂**

不要在工厂方法创建的列表中存放null元素。

```java
// 创建一个由少量元素构成的列表
List<String> friends = new ArrayList<>();
friends.add("Raphael");
friends.add("Olivia");
friends.add("Thibaut");

List<String> friends = Arrays.asList("Raphael", "Olivia", "Thibaut");  // 创建出的列表是固定大小的，不能插入或删除
// 可以使用friends.set更新元素


// Java 9 中新的工厂方法
// List工厂
List<String> friends = List.of("Raphael", "Olivia"，"Thibaut");
// Set工厂
Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");   // 不要出现重复元素，会抛异常
// Map工厂
Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 20); // 参数是交替的键值

Map<String, Integer> ageOfFriends = Map.ofEntries(entry("Raphael", 30), entry("Olivia", 20)); //如果超过10个键值对，可以采用这种办法
```

**使用List和Set**

Java8在List和Set的接口中新引入了以下方法：

- `removeIf`List和Set中都实现了这个方法，移除集合中匹配指定谓词的元素
- `replaceAll`用于List，使用一个函数（`UnaryOperator`)替换元素
- `sort`用于List，对列表自身的元素进行排序

**使用Map**

```java
// forEach方法
ageOfFriends.forEach((friend, age) -> System.out.println(friend + ": " + age));
// Map排序， Entry.comparingByValue         Entry.comparingByKey
ageOfFriends.entrySet().stream().sorted(Entry.comparingByKey());
// getOrDefault方法
ageOfFriends.getOrDefault("Raphel", 10);

// 计算模式
// computeIfAbsent	如果指定的键没有对应的值，那么使用该键计算新的值，并将其添加到Map中

// computeIfPresent 如果指定的键在Map中存在，就计算该键的新值，并将其添加到Map中

// compute 使用指定键计算新的值，并将其储存到Map中

// 删除模式 删除某个键值对
ageOfFriends.remove(key, value);

// 替换模式，replaceAll--通过BiFunction替换Map中每个项的值。 Replace--如果键存在，就可以通过该方法替换Map中该键对应的值

// merge方法  Java8中新增的方法
Map<String, String> everyone = new HashMap<>(family);
friends.forEach((k, v) -> 
               everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
```



## 第 9 章 重构、测试和调试

**本章内容**

- 如何使用lambda表达式重构代码
- Lambda表达式对面向对象的设计模式的影响
- Lambda表达式的测试
- 如何调试使用Lambda表达式和Stream API的代码



匿名类和Lambda表达式的区别：

- 匿名类和Lambda表达式中的this和super含义是不同的，在匿名类中this代表的是类本身，但是在lambda中，它代表的是外部所在类。
- 其次，匿名类可以屏蔽外部类的变量，但是Lambda表达式不能。
- 在涉及重载的上下文里，将匿名类转换为Lambda表达式可能导致最终代码更为晦涩。



没有函数接口就无法使用Lambda表达式，什么情况下使用函数接口呢？有条件的延迟执行和环绕执行。

```java
if (logger.isLoogable(Log.FINER){
    logger.finer("Problem: " + generateDiagnostic());
})
```

以上这段代码的问题：

- 日志器的状态（它支持哪些日志等级）通过`isLoggable`方法暴露给了客户端代码。
- 为什么要在每次输出一条日志之前都去查询日志器对象的状态？



**策略模式**

策略模式代表了一种解决一类算法的通用解决方案，你可以在运行时选择使用哪种方案。

策略模式包含三部分内容：

- 一个代表某个算法的接口（Strategy接口）
- 一个或多个该接口的具体实现，它们代表了算法的多种实现（比如，实体类ConcreteSrategyA或ConcreteStrategyB）
- 一个或多个使用策略对象的客户

假设你希望验证输入的内容是否根据标准进行了恰当的格式化（比如只包含小写字母或数字）。你可以从定义一个验证文本的接口入手：

```java
public interface ValidationStrategy{
    boolean execute(String s);
}
// 然后定义多种实现
public class IsAllLowerCase implements ValidationStrategy{
    public boolean execute(String s){
        return s.matches("[a-z]+");
    }
}
public class IsNumeric implements ValidationStrategy{
    public boolean execute(String s){
        return s.matches("\\d+");
    }
}

// 之后就可以在程序中使用这些略有差异的验证策略了
public class Validator{
    private final ValidationStrategy strategy;
    public Validator(ValidationStrategy v){
        this.strategy = v;
    }
    public boolean validate(String s){
        returns strategy.execute(s);
    }
}
```

**模板方法**

如果你需要采用某个算法的框架，同时又希望有一定的灵活度，能对它的某些部分进行改进，那么采用模版方法设计模式是比较通用的解决方案。

```java
public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
  Customer costomer = Database.getCustomerWithId(id);
  makeCustomerHappy.accept(c);
}
```

**观察者模式**

某些事件发生时，如果一个对象需要自动地通知其他多个对象，就会采用该方案。

```java
interface Observer {
  void notify(String tweet);
}
class NYTimes implements Observer{
  public void notify(String tweet){
    if(tweet != null && tweet.contains("money")){
      System.out.println("Breaking news in NY!" + tweet);
    }
  }
}

class Guardian implements Observer{
  public void notify(String tweet){
    if(tweet != null && tweet.contains("queen")){
      System.out.println("Yet more news from London..." + tweet);
    }
  }
}

interface Subject{
  void registerObserver(Observer o);
  void notifyObservers(String tweet);
}

class Feed implements Subject{
  private final List<Observer> observers = new ArrayList<>();
  public void registerObserver(Observer o){
    this.observers.add(o);
  }
  
  public void notifyObservers(String tweet){
    observers.forEach(o -> o.notify(tweet));
  }
}
```

**责任链模式**

责任链模式是一种创建处理对象序列（比如操作序列）的通用方案。一个处理对象可能需要再完成一些工作之后，将结果传递给另一个对象，这个对象接着做一些工作，再转交给下一个处理对象。

**工厂模式**

使用工厂模式，你无需项客户暴露实例化的逻辑就能完成对象的创建。





## 第 12 章  新的日期和时间API



**使用`LocalDate`和`LocalTime` **

```java
LocalDate date = LocalDate.of(2017, 9, 21);
int year = date.getYear();
Month month = date.getMonth();
int day = date.getDayOfMonth();
DayOfWeek dow = date.getDayOfWeek();
boolean leap = date.isLeapYear();

// 也可以使用工厂方法now从系统时钟中获取当前的日期
LocalDate today = LocalDate.now();
```

可以通过传递一个`TemporalField`参数给get方法访问日期信息， `ChronoField`枚举实现了`temporal`接口

```java
int year = date.get(ChronoField.YEAR);
int month = date.get(ChronoField.MONTH_OF_YEAR);
int day = date.get(ChronoField.DAY_OF_MONTH);
```

可以使用Java内建的方法访问

```java
int year = date.getYear();
int month = date.getMonthValue();
int day = date.getDayOfMonth();
```

创建`LocalTime`

```java
LocalTime time = LocalTime.of(12, 45, 20);
int hour = time.getHour();
int minute = time.getMinute();
int second = time.getSecond();
```

```java
// LocalDate和LocalTime都可以解析代表它们的字符串创建
LocalDate date = LocalDate.parse("2017-09-21");
LocalTime time = LocalTime.parse("13:45:20");
```

**合并日期和时间**

这个复合类名叫`LocalDateTime`，是`LocalDate`和`LocalTime`的合体，它同时表示了日期和时间，但不带有时区信息。

```java
LocalDateTime dt1 = LocalDateTime.of(2014, Month.SEPTEMBER, 21, 13, 45, 20);
LocalDateTime dt2 = LocalDateTime.of(date, time);
LocalDateTime dt3 = date.atTime(13, 45, 20);
LocalDateTime dt4 = date.atTime(time);
LocalDateTime dt5 = time.atDate(date);
```

**提取日期或时间**

```java
LocalDate date1 = dt1.toLocalDate();
LocalTime time1 = dt1.toLocalTime();
```



**机器的日期和时间格式**

```java
Instant.ofEpochSecond(3);
Instant.ofEpochSecond(3, 0);
Instant.ofEpochSecond(2, 1_000_000_000); 	// 2秒之后再加上10亿纳秒
Instant.ofEpochSecond(4, -1_000_000_000);
// Instant类也支持静态工厂方法now
int day = Instant.now().get(ChronoField.DAY_OF_MONTH)
```

**定义Duration或Period**

```java
// 可以创建两个LocalTime对象、两个LocalDateTime对象、或者两个Instant对象之间的Duration
// Duration 主要用秒或纳秒衡量时间的长短
Duration d1 = Duration.between(time1, time2);
Duration d2 = Duration.between(dateTime1, dateTime2);
Duration d3 = Duration.between(instant1, instant2);

// 如果需要以年、月或日的方式对多个时间单位建模，可以使用Period类
Period tenDays = Period.between(LocalDate.of(2017, 9, 11), LocalDate.of(2017, 9,21));

// 使用工厂类创建Duration或Period
Duration threeMinutes = Duration.ofMinute(3);
Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);

Period tenDays = Period.ofDays(10);
Period threeWeeks = Period.ofWeeks(3);
Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
```

**操纵、解析和格式化日期**

```java
// 使用withAttribute方法创建对象的副本来修改日期
LocalDate date1 = LocalDate.of(2017, 9, 21);    // 2017-09-21
LocalDate date2 = date1.withYear(2011);			// 2011-09-21
LocalDate date3 = date2.withDayOfMonth(25);    // 2011-09-25
LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2);

// 以相对的方式修改LocalDate对象的属性
LocalDate date1 = LocalDate.of(2017, 9, 21);
LocalDate date2 = date1.plusWeeks(1);
LocalDate date3 = date2.minusYears(6);
LocalDate date4 = date3.plus(6, ChronoUnit,MONTHS);
```

**使用TemporalAdjuster**

如果需要将日期调整到下个周日、下个工作日、或者是本月的最后一天，可以使用重载版的with方法，并向其提供一个`TemporalAdjuster`

```java
import static java.time.temporal.TemporalAdjusters.*;
LocalDate date1 = LocalDate.of(2014, 3, 18);
LocalDate date2 = date1.with(nextOrSame(DayOfWeek,SUNDAY));
LocalDate date3 = date2.with(lastDayOfMonth());
```



