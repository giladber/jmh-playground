package iaf.ofek.perf;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by giladrber on 3/29/2017.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class RandomBenchmark {

    Random rnd;

    @Setup(Level.Iteration)
    public void setup() {
        rnd = new Random(System.nanoTime());
    }

    @Benchmark
    @GroupThreads(8)
    public long standardRandom()
    {
        return rnd.nextLong();
    }

    @Benchmark
    @GroupThreads(8)
    public long tlRandom() {
        return ThreadLocalRandom.current().nextLong();
    }


    /*
     * Not many people are aware of the difference between Random and ThreadLocalRandom, let alone of the existence
     * of ThreadLocalRandom.
     * Using random incurs a synchronization overhead which ThreadLocalRandom helps avoid.
     * This benchmark demonstrates why using TLR is always better (even in the non-contended case).
     *
     * Basic results: (2 threads)

     Benchmark                                                Mode      Cnt       Score   Error   Units
    RandomBenchmark.standardRandom                         sample  2503369       0.476 ± 0.287   us/op
    RandomBenchmark.standardRandom:standardRandom·p0.00    sample                  ≈ 0           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.50    sample                  ≈ 0           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.90    sample                0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.95    sample                0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.99    sample                0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.999   sample               11.840           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.9999  sample              249.252           us/op
    RandomBenchmark.standardRandom:standardRandom·p1.00    sample           206569.472           us/op
    RandomBenchmark.tlRandom                               sample  2822656       0.186 ± 0.016   us/op
    RandomBenchmark.tlRandom:tlRandom·p0.00                sample                  ≈ 0           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.50                sample                  ≈ 0           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.90                sample                0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.95                sample                0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.99                sample                0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.999               sample                2.368           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.9999              sample               78.464           us/op
    RandomBenchmark.tlRandom:tlRandom·p1.00                sample             4038.656           us/op

     * .. But the histogram tells the real story:
     * TLR:

     Histogram, us/op:
    [   0.000,  500.000) = 2822602
    [ 500.000, 1000.000) = 17
    [1000.000, 1500.000) = 13
    [1500.000, 2000.000) = 9
    [2000.000, 2500.000) = 8
    [2500.000, 3000.000) = 2
    [3000.000, 3500.000) = 3
    [3500.000, 4000.000) = 1
    [4000.000, 4500.000) = 1

     * Standard Random:

     Histogram, us/op:
    [     0.000,  25000.000) = 2503367
    [ 25000.000,  50000.000) = 0
    [ 50000.000,  75000.000) = 1
    [ 75000.000, 100000.000) = 0
    [100000.000, 125000.000) = 0
    [125000.000, 150000.000) = 0
    [150000.000, 175000.000) = 0
    [175000.000, 200000.000) = 0
    [200000.000, 225000.000) = 1
    [225000.000, 250000.000) = 0
    [250000.000, 275000.000) = 0

     * Using one thread only:

    Benchmark                                                Mode      Cnt      Score   Error   Units
    RandomBenchmark.standardRandom                         sample  1420212      0.274 ± 0.083   us/op
    RandomBenchmark.standardRandom:standardRandom·p0.00    sample                 ≈ 0           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.50    sample                 ≈ 0           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.90    sample               0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.95    sample               0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.99    sample               0.395           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.999   sample               3.156           us/op
    RandomBenchmark.standardRandom:standardRandom·p0.9999  sample              80.896           us/op
    RandomBenchmark.standardRandom:standardRandom·p1.00    sample           33161.216           us/op
    RandomBenchmark.tlRandom                               sample  1295544      0.139 ± 0.005   us/op
    RandomBenchmark.tlRandom:tlRandom·p0.00                sample                 ≈ 0           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.50                sample                 ≈ 0           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.90                sample               0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.95                sample               0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.99                sample               0.395           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.999               sample               0.790           us/op
    RandomBenchmark.tlRandom:tlRandom·p0.9999              sample              60.523           us/op
    RandomBenchmark.tlRandom:tlRandom·p1.00                sample             972.800           us/op

     */

}
