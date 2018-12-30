package composable

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class ShoppingAPITest extends Specification {

    def "test buy"() {
        given:
        def items = [new Item(1), new Item(2)]

        when:
        def cart = ShoppingAPI.buy().apply(items)

        then:
        cart.items == items
    }

    def "test order"() {
        given:
        def items = [new Item(1), new Item(2)]
        def cart = new Cart(items)

        when:
        def order = ShoppingAPI.order().apply(cart)

        then:
        order.cart == cart
    }

    def "test deliver"() {
        given:
        def items = [new Item(1), new Item(2)]
        def cart = new Cart(items)
        def order = new Order(cart)
        
        when:
        def delivery = ShoppingAPI.deliver().apply(order)
        
        then:
        delivery.order == order
    }

    def "test oneClickBuy"() {
        given:
        def items = [new Item(1), new Item(2)]

        when:
        def delivery = ShoppingAPI.oneClickBuy().apply(items)

        then:
        delivery.order.cart.items == items
    }
}
