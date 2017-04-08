package iaf.ofek.perf;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *
 * Created by giladrber on 3/29/2017.
 */
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class BranchPredictionBenchmark {

    @Param({"100", "10000", "1000000"})
    static int arraySize;

    @State(Scope.Benchmark)
    public static class SortedData
    {
        @Setup(Level.Iteration)
        public void setup() {
            data = makeRandomArray(arraySize);
            Arrays.sort(data);
            median = data[data.length / 2 - 1];
        }

        int[] data;
        int median;
    }

    @State(Scope.Benchmark)
    public static class UnsortedData
    {
        @Setup(Level.Iteration)
        public void setup() {
            data = makeRandomArray(arraySize);
            int[] tmp = new int[data.length];
            System.arraycopy(data, 0, tmp , 0, data.length);
            Arrays.sort(tmp);
            median = tmp[tmp.length / 2 - 1];
        }

        int[] data;
        int median;
    }

    public static int[] makeRandomArray(int size) {
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = ThreadLocalRandom.current().nextInt();
        }

        return result;
    }

    @Benchmark
    public int countSorted(SortedData data) {
        int counter = 0;

        for (int i : data.data) {
            if (i > data.median) {
                counter++;
            }
        }

        return counter;
    }

    @Benchmark
    public int countUnsorted(UnsortedData data) {
        int counter = 0;

        for (int i : data.data) {
            if (i > data.median) {
                counter++;
            }
        }

        return counter;
    }
}
