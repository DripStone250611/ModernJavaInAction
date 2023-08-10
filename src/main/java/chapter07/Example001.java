package chapter07;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @author : YINAN
 * @date : 2023/8/10
 * @effect :
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2, jvmArgs={"-Xms4G", "-Xmx4G"})
public class Example001 {

    private static final long N = 10_000_000L;

    @Benchmark
    public long sequentialSum(){
        return Stream.iterate(1L, i->i+1).limit(N).reduce(0L, Long::sum);
    }

//    @TearDown(Level.Invocation)
//    public void tearDown(){
//        System.gc();
//    }
}
