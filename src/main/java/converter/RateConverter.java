package converter;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
public class RateConverter implements CurrableDoubleBinaryOperator {

    @Override
    public double applyAsDouble(double value, double rate) {
        return value * rate;
    }

    static DoubleUnaryOperator milesToKmConverter() {
        return new RateConverter().rate(1.609);
    }

    static DoubleUnaryOperator celsiusToFahrenheitConverter() {
        return new RateConverter().rate(1.8).andThen(x -> x + 32);
    }
}
