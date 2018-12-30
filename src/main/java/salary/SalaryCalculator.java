package salary;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class SalaryCalculator {
    private final DoubleUnaryOperator operator;

    SalaryCalculator() {
        this(DoubleUnaryOperator.identity());
    }

    private SalaryCalculator(DoubleUnaryOperator operator) {
        this.operator = operator;
    }

    SalaryCalculator with(SalaryRules rule) {
        return new SalaryCalculator(operator.andThen(rule.operator));
    }

    double calculate(double salary) {
        return operator.applyAsDouble(salary);

    }
}