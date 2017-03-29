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
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class StringBenchmark
{

    private static final String BLA = "BLA ";
    private static final String BLABLA = " BLA BLA";
    private static final String MSG = BLA + "%s" + BLABLA;

    int filler;

    @Setup(Level.Iteration)
    public void setup() {
        filler = ThreadLocalRandom.current().nextInt();
    }

    @Benchmark
    public String formatBenchmark() {
        return String.format(MSG, filler);
    }

    @Benchmark
    public String plusBenchmark() {
        return "BLA " + filler + " BLA BLA";
    }

    @Benchmark
    public String builderBenchmark() {
        StringBuilder builder = new StringBuilder();
        builder.append(BLA)
                .append(filler)
                .append(BLABLA);

        return builder.toString();
    }

    @Benchmark
    public String smartBuilderBenchmark() {
        StringBuilder builder = new StringBuilder(BLA.length() + BLABLA.length());

        builder.append(BLA)
                .append(filler)
                .append(BLABLA);

        return builder.toString();
    }

    /*
     * I've run into many cases where string allocation caused excessive gc cycles - we would then question
     * whether it is feasible to replace the standard string concatenation (using +) with an explicit StringBuilder
     * or using String.format.
     * According to this benchmark, using concatenation is the best allocation-wise (per operation).
     * Using String.format is obviously the worst option, both allocation wise and throughput wise.
     * Builder and smart builder are not significantly different and show no specific reason to use them.
     * Conclusion: JVM is pretty good at performing optimizations, just let it do its thing.

     Benchmark                                                                Mode  Cnt     Score     Error   Units
     StringBenchmark.builderBenchmark                                        thrpt   45     2.053 ±   0.443  ops/us
     StringBenchmark.builderBenchmark:·gc.alloc.rate                         thrpt   45   323.079 ±  69.682  MB/sec
     StringBenchmark.builderBenchmark:·gc.alloc.rate.norm                    thrpt   45   247.645 ±   0.876    B/op
     StringBenchmark.builderBenchmark:·gc.churn.PS_Eden_Space                thrpt   45   305.176 ± 150.805  MB/sec
     StringBenchmark.builderBenchmark:·gc.churn.PS_Eden_Space.norm           thrpt   45   229.748 ± 116.008    B/op
     StringBenchmark.builderBenchmark:·gc.churn.PS_Survivor_Space            thrpt   45     0.026 ±   0.072  MB/sec
     StringBenchmark.builderBenchmark:·gc.churn.PS_Survivor_Space.norm       thrpt   45     0.032 ±   0.097    B/op
     StringBenchmark.builderBenchmark:·gc.count                              thrpt   45    26.000            counts
     StringBenchmark.builderBenchmark:·gc.time                               thrpt   45    36.000                ms
     StringBenchmark.formatBenchmark                                         thrpt   45     0.242 ±   0.009  ops/us
     StringBenchmark.formatBenchmark:·gc.alloc.rate                          thrpt   45   153.396 ±   6.030  MB/sec
     StringBenchmark.formatBenchmark:·gc.alloc.rate.norm                     thrpt   45   998.939 ±   2.122    B/op
     StringBenchmark.formatBenchmark:·gc.churn.PS_Eden_Space                 thrpt   45   156.112 ± 119.010  MB/sec
     StringBenchmark.formatBenchmark:·gc.churn.PS_Eden_Space.norm            thrpt   45  1037.965 ± 804.937    B/op
     StringBenchmark.formatBenchmark:·gc.churn.PS_Survivor_Space             thrpt   45     0.068 ±   0.133  MB/sec
     StringBenchmark.formatBenchmark:·gc.churn.PS_Survivor_Space.norm        thrpt   45     0.491 ±   0.960    B/op
     StringBenchmark.formatBenchmark:·gc.count                               thrpt   45    15.000            counts
     StringBenchmark.formatBenchmark:·gc.time                                thrpt   45    77.000                ms
     StringBenchmark.plusBenchmark                                           thrpt   45     4.557 ±   0.313  ops/us
     StringBenchmark.plusBenchmark:·gc.alloc.rate                            thrpt   45   253.823 ±  17.480  MB/sec
     StringBenchmark.plusBenchmark:·gc.alloc.rate.norm                       thrpt   45    87.645 ±   0.876    B/op
     StringBenchmark.plusBenchmark:·gc.churn.PS_Eden_Space                   thrpt   45   258.576 ± 140.531  MB/sec
     StringBenchmark.plusBenchmark:·gc.churn.PS_Eden_Space.norm              thrpt   45    88.885 ±  49.107    B/op
     StringBenchmark.plusBenchmark:·gc.churn.PS_Survivor_Space               thrpt   45     0.067 ±   0.126  MB/sec
     StringBenchmark.plusBenchmark:·gc.churn.PS_Survivor_Space.norm          thrpt   45     0.026 ±   0.049    B/op
     StringBenchmark.plusBenchmark:·gc.count                                 thrpt   45    22.000            counts
     StringBenchmark.plusBenchmark:·gc.time                                  thrpt   45    45.000                ms
     StringBenchmark.smartBuilderBenchmark                                   thrpt   45     1.953 ±   0.078  ops/us
     StringBenchmark.smartBuilderBenchmark:·gc.alloc.rate                    thrpt   45   278.039 ±  11.119  MB/sec
     StringBenchmark.smartBuilderBenchmark:·gc.alloc.rate.norm               thrpt   45   224.001 ±   0.001    B/op
     StringBenchmark.smartBuilderBenchmark:·gc.churn.PS_Eden_Space           thrpt   45   282.116 ± 140.269  MB/sec
     StringBenchmark.smartBuilderBenchmark:·gc.churn.PS_Eden_Space.norm      thrpt   45   227.281 ± 113.992    B/op
     StringBenchmark.smartBuilderBenchmark:·gc.churn.PS_Survivor_Space       thrpt   45     0.048 ±   0.108  MB/sec
     StringBenchmark.smartBuilderBenchmark:·gc.churn.PS_Survivor_Space.norm  thrpt   45     0.047 ±   0.107    B/op
     StringBenchmark.smartBuilderBenchmark:·gc.count                         thrpt   45    24.000            counts
     StringBenchmark.smartBuilderBenchmark:·gc.time                          thrpt   45    39.000                ms


     */

}
