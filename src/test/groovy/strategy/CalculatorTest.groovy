package strategy

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-30.
 */
class CalculatorTest extends Specification {
    
    def "test totalValues even"() {
        given:
        def integers = [1, 2, 3, 4, 5, 6, 7]

        when:
        def sum = Calculator.totalValues(integers, { it % 2 == 0 })

        then:
        sum == 12
    }

    def "test totalValues odd"() {
        given:
        def integers = [1, 2, 3, 4, 5, 6, 7]

        when:
        def sum = Calculator.totalValues(integers, { it % 2 != 0 })

        then:
        sum == 16
    }
}
