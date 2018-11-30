package strategy;

import java.util.List;
import java.util.function.IntPredicate;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
final class Calculator {
    static int totalValues(List<Integer> integers, IntPredicate take) {
        return integers.stream()
                .mapToInt(i -> i)
                .filter(take)
                .sum();
    }
}
