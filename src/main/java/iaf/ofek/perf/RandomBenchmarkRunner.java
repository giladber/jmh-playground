package iaf.ofek.perf;

import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * Created by giladrber on 3/29/2017.
 */
public class RandomBenchmarkRunner {

    public static void main(String... args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .addProfiler(GCProfiler.class)
                .measurementIterations(15)
                .warmupIterations(10)
                .threads(1)
                .forks(3)
                .jvmArgs("-Xms1g", "-Xmx1g", "-Xmn800m")
                .include(RandomBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }


}
