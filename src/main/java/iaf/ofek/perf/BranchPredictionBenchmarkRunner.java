package iaf.ofek.perf;

import org.openjdk.jmh.profile.SafepointsProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * Created by giladrber on 3/29/2017.
 */
public class BranchPredictionBenchmarkRunner
{

    public static void main(String... args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .measurementIterations(15)
                .warmupIterations(10)
                .forks(2)
                .threads(1)
                .addProfiler(StackProfiler.class)
                .jvmArgs("-Xms1g", "-Xmx1g", "-Xmn800m")
                .shouldDoGC(true)
                .include(BranchPredictionBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

}
