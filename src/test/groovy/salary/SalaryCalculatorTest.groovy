package salary

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class SalaryCalculatorTest extends Specification {
    def "test calculate: bonus, addition and taxation"() {
        given:
        def calculator = new SalaryCalculator().with(SalaryRules.BONUS)
                .with(SalaryRules.ADDITION)
                .with(SalaryRules.TAX)
        
        expect:
        calculator.calculate(1000) == 1053
    }
}
