package iaf.ofek.perf;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * Created by giladrber on 3/29/2017.
 */
public class MultiplicationBenchmarkRunner {

    public static void main(String... args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .measurementIterations(15)
                .warmupIterations(10)
                .forks(5)
                .threads(1)
                .jvmArgs("-Xms1g", "-Xmx1g", "-Xmn800m", "-server")
                .include(MultiplicationBenchmark.class.getSimpleName())
                .build();

        new Runner(opts).run();
    }

}
