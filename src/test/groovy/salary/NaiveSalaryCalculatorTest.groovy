package salary

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class NaiveSalaryCalculatorTest extends Specification {
    def "test calculate: bonus, addition and taxation"() {
        given:
        def calculator = new NaiveSalaryCalculator().with(SalaryRules.BONUS)
                .with(SalaryRules.ADDITION)
                .with(SalaryRules.TAX)

        expect:
        calculator.calculate(1000) == 1053
    }
}
