package customer

import com.google.common.collect.ImmutableList
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-24.
 */
class CustomerTest extends Specification {
    def "test findOrderWithMaxPrice"() {
        given:
        def orders = ImmutableList.of(
                Order.builder()
                        .id(1)
                        .price(BigDecimal.valueOf(15.26))
                        .build(),
                Order.builder()
                        .id(2)
                        .price(BigDecimal.valueOf(9.99))
                        .build(),
                Order.builder()
                        .id(3)
                        .price(BigDecimal.valueOf(120.43))
                        .build()
        )

        def customer = Customer.builder()
                .orders(orders)
                .build()

        when:
        def maxOrderOptional = customer.findOrderWithMaxPrice()

        then:
        maxOrderOptional == Optional.of(orders.max { it.price })
    }

    def "test findOrderWithMaxPrice - one order with null price"() {
        given:
        def orders = ImmutableList.of(
                Order.builder()
                        .id(1)
                        .price(BigDecimal.valueOf(15.26))
                        .build(),
                Order.builder()
                        .id(2)
                        .price(null)
                        .build(),
                Order.builder()
                        .id(3)
                        .price(BigDecimal.valueOf(120.43))
                        .build()
        )

        def customer = Customer.builder()
                .orders(orders)
                .build()

        when:
        def maxOrderOptional = customer.findOrderWithMaxPrice()

        then:
        maxOrderOptional == Optional.of(orders.max { it.price })
    }

    def "test findOrderWithMaxPrice - all orders with null price"() {
        given:
        def orders = ImmutableList.of(
                Order.builder()
                        .id(1)
                        .price(null)
                        .build(),
                Order.builder()
                        .id(2)
                        .price(null)
                        .build(),
                Order.builder()
                        .id(3)
                        .price(null)
                        .build()
        )

        def customer = Customer.builder()
                .orders(orders)
                .build()

        when:
        def maxOrderOptional = customer.findOrderWithMaxPrice()

        then:
        maxOrderOptional == Optional.of(orders.max { it.price })
    }

    def "test findOrderWithMaxPrice - empty orders"() {
        given:
        def customer = Customer.builder()
                .orders(null)
                .build()

        when:
        def maxOrderOptional = customer.findOrderWithMaxPrice()

        then:
        maxOrderOptional == Optional.empty()
    }
}
