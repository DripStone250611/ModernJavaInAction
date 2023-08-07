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

public static List<Apple> filterApples2(List<Apple> inventory, ApplePredicate p){
    List<Apple> result = new ArrayList<>();
    for(Apple apple : inventory){
        if(p.test(apple)){
            result.add(apple);
        }
    }
    return result;
}

//使用
List<Apple> lst = filterApples2(apples, new AppleGreenColorPredicate);
```



