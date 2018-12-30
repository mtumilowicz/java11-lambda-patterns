package composable;

import java.util.List;
import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class ShoppingAPI {
    static Function<List<Item>, Cart> buy() {
        return Cart::new;
    }

    static Function<Cart, Order> order() {
        return Order::new;
    }

    static Function<Order, Delivery> deliver() {
        return Delivery::new;
    }

    static Function<List<Item>, Delivery> oneClickBuy() {
        return buy()
                .andThen(order())
                .andThen(deliver());
    }
}
