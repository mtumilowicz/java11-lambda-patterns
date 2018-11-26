package converter;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
@FunctionalInterface
public interface CurrableDoubleBinaryOperator extends DoubleBinaryOperator {
    
    default DoubleUnaryOperator curry(double t) {
        return u -> applyAsDouble(t, u);
    }

    default DoubleUnaryOperator rcurry(double u) {
        return t -> applyAsDouble(t, u);
    }
}
