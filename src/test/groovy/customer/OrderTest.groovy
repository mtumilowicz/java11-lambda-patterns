package customer

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-25.
 */
class OrderTest extends Specification {
    def "test not empty price"() {
        given:
        def order = Order.builder()
                .price(BigDecimal.ONE)
                .build()
        expect:
        order.hasPrice()
    }

    def "test empty price"() {
        given:
        def order = Order.builder()
                .build()
        expect:
        !order.hasPrice()
    }
}
