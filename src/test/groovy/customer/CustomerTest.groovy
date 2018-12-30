package customer

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import org.apache.commons.lang3.tuple.ImmutableTriple
import spock.lang.Specification

import java.time.Year

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
        maxOrderOptional == Optional.ofNullable(orders.findAll { it.hasPrice() }.max { it.price })
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

    def "findTop3OrdersByPrice empty order list"() {
        given:
        def customer = Customer.builder()
                .orders(null)
                .build()

        expect:

        customer.findTop3OrdersByPrice() == ImmutableTriple.nullTriple()
    }

    def "findTop3OrdersByPrice one element order list"() {
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
                        .price(null)
                        .build()
        )

        def customer = Customer.builder()
                .orders(orders)
                .build()

        when:
        def top3 = customer.findTop3OrdersByPrice()

        then:
        top3.left.id == 1
        !top3.middle
        !top3.right
    }

    def "findTop3OrdersByPrice two elements order list"() {
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
        def top3 = customer.findTop3OrdersByPrice()

        then:
        top3.left.id == 3
        top3.middle.id == 1
        !top3.right
    }

    def "findTop3OrdersByPrice three elements order list"() {
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
        def top3 = customer.findTop3OrdersByPrice()

        then:
        top3.left.id == 3
        top3.middle.id == 1
        top3.right.id == 2
    }

    def "findTop3OrdersByPrice four elements order list"() {
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
                        .build(),
                Order.builder()
                        .id(4)
                        .price(BigDecimal.valueOf(76.50))
                        .build()
        )

        def customer = Customer.builder()
                .orders(orders)
                .build()

        when:
        def top3 = customer.findTop3OrdersByPrice()

        then:
        top3.left.id == 3
        top3.middle.id == 4
        top3.right.id == 1
    }

    def "yearTagsExpensesMap null expenses"() {
        given:
        def customer = Customer.builder().build()

        expect:
        customer.yearTagsExpensesMap() == ImmutableMap.of()
    }

    def "yearTagsExpensesMap - two different years, many different and repeatable tags"() {
        given:
        def expenses = ImmutableList.of(
                Expense.builder()
                        .year(Year.of(1990))
                        .tags(ImmutableSet.of("travel", "entertainment", "food",
                        "car", "gasoline"))
                        .build(),
                Expense.builder()
                        .year(Year.of(1990))
                        .tags(ImmutableSet.of("food", "entertainment", "cinema"))
                        .build(),
                Expense.builder()
                        .year(Year.of(2000))
                        .tags(ImmutableSet.of("clothes", "electronics"))
                        .build()
        )

        and:
        def customer = Customer.builder()
                .expenses(expenses)
                .build()

        when:
        def yearTagsExpensesMap = customer.yearTagsExpensesMap()

        then:
        yearTagsExpensesMap[Year.of(1990)] == ["entertainment",
                                               "car",
                                               "gasoline",
                                               "cinema",
                                               "travel",
                                               "food"] as Set
        yearTagsExpensesMap[Year.of(2000)] == ["clothes",
                                               "electronics"] as Set
    }
}
