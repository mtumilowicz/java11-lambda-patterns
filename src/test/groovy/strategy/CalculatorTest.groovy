package strategy

import spock.lang.Specification

import java.util.function.IntUnaryOperator

/**
 * Created by mtumilowicz on 2018-11-30.
 */
class CalculatorTest extends Specification {

    def "test totalValues < 4"() {
        given:
        def stocks = [new Stock(1),
                      new Stock(2),
                      new Stock(3),
                      new Stock(4),
                      new Stock(5),
                      new Stock(6),
                      new Stock(7)]
        
        def calculator = new Calculator(new PriceProvider(IntUnaryOperator.identity()))

        when:
        def sum = calculator.totalValues(stocks, Calculator.priceLessThan(4))

        then:
        sum == 6
    }

    def "test totalValues <= 5"() {
        given:
        def stocks = [new Stock(1),
                      new Stock(2),
                      new Stock(3),
                      new Stock(4),
                      new Stock(5),
                      new Stock(6),
                      new Stock(7)]

        def calculator = new Calculator(new PriceProvider(IntUnaryOperator.identity()))

        when:
        def sum = calculator.totalValues(stocks, Calculator.priceLessThan(3) | Calculator.priceEquals(5))

        then:
        sum == 8
    }
}
