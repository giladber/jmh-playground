package iaf.ofek.perf;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by giladrber on 3/29/2017.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MultiplicationBenchmark
{

    long state;

    @Setup(Level.Iteration)
    public void setup() {
        state = ThreadLocalRandom.current().nextLong();
    }

    @Benchmark
    public long mul2() {
        return  2 * state;
    }

    @Benchmark
    public long mul16() {
        return 16 * state;
    }

    @Benchmark
    public long mul2bit() {
        return state << 1;
    }

    @Benchmark
    public long mul16bit() {
        return state << 4;
    }



}
