package salary;

import converter.RateConverter;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
public enum SalaryRules {
    TAX(new RateConverter().rate(0.81)),
    BONUS(new RateConverter().rate(1.2)),
    ADDITION(salary -> salary + 100);

    public final DoubleUnaryOperator operator;

    SalaryRules(DoubleUnaryOperator operator) {
        this.operator = operator;
    }
}
