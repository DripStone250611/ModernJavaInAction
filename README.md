# Modern In Java

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
