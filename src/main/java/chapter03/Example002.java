package chapter03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author : YINAN
 * @date : 2023/8/7
 * @effect : 读取文件
 */
public class Example002 {
    public static void main(String[] args) throws IOException {
        System.out.println(processFile((BufferedReader br) -> {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static String processFile(Function<BufferedReader, String> func) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader("C:\\yinan\\Java\\ModernJavaInAction\\src\\main\\java\\chapter03\\Example001.java"))){
            return func.apply(br);
        }
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> results = new ArrayList<>();
        for (T s: list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }

    public static <T> void forEach(List<T> list, Consumer<T> c){
        for (T i:list){
            c.accept(i);
        }
    }
}
