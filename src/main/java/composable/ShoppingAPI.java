package composable;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class ShoppingAPI {
    private static Function<List<Item>, Cart> buy() {
        return list -> Cart.builder().items(ImmutableList.copyOf(list)).build();
    }

    private static Function<Cart, Order> order() {
        return cart -> Order.builder().cart(cart).build();
    }

    private static Function<Order, Delivery> deliver() {
        return order -> Delivery.builder().order(order).build();
    }

    public static Function<List<Item>, Delivery> oneClickBuy() {
        return buy()
                .andThen(order())
                .andThen(deliver());
    }
}
