package salary;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
public class SalaryCalculator2 {
    private final DoubleUnaryOperator operator;

    public SalaryCalculator2() {
        this(DoubleUnaryOperator.identity());
    }

    private SalaryCalculator2(DoubleUnaryOperator operator) {
        this.operator = operator;
    }

    public SalaryCalculator2 with(SalaryRules rule) {
        return new SalaryCalculator2(operator.andThen(rule.operator));
    }

    public double calculate(double salary) {
        return operator.applyAsDouble(salary);

    }
}
