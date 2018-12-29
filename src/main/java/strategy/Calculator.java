package strategy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-30.
 */

@Value
class Calculator {
    PriceProvider priceProvider;

    int totalValues(List<Stock> integers, IntPredicate take) {
        return integers.stream()
                .map(Stock::getId)
                .mapToInt(priceProvider::getPrice)
                .filter(take)
                .sum();
    }


    static IntPredicate priceLessThan(int limit) {
        return it -> it < limit;
    }

    static IntPredicate priceEquals(int limit) {
        return it -> it == limit;
    }
}

@Value
class Stock {
    int id;
}

@Value
class PriceProvider {
    @Getter(AccessLevel.NONE)
    IntUnaryOperator priceSource;

    int getPrice(int id) {
        return priceSource.applyAsInt(id);
    }
}
