package salary;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class NaiveSalaryCalculator {
    private final List<SalaryRules> operators = new LinkedList<>();

    NaiveSalaryCalculator with(SalaryRules rule) {
        operators.add(rule);

        return this;
    }

    double calculate(double salary) {
        return operators.stream()
                .map(rule -> rule.operator)
                .reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::andThen)
                .applyAsDouble(salary);

    }
}