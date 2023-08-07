package chapter03;


/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect : 多线程
 */
public class Example001 {

    public static void main(String[] args){
        Runnable r1 = () -> System.out.println("Hello World 1");

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };


        process(r1);
        process(r2);
        process(() -> System.out.println("Hello World 3"));

    }
    public static void process(Runnable r){
        r.run();
    }
}
