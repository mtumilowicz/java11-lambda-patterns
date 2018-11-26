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
    
    public static DoubleUnaryOperator milesToKmConverter() {
        return new RateConverter().rcurry(1.609);
    }
}
