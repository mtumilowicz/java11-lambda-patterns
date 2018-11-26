package salary;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
public class SalaryCalculator {
    private final List<SalaryRules> operators = new LinkedList<>();

    public SalaryCalculator with(SalaryRules rule) {
        operators.add(rule);

        return this;
    }

    public double calculate(double salary) {
        return operators.stream()
                .map(x -> x.operator)
                .reduce(x -> x, DoubleUnaryOperator::andThen)
                .applyAsDouble(salary);

    }
}
