package composable;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.util.List;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
@Value
class Cart {
    ImmutableList<Item> items;

    Cart(List<Item> items) {
        this.items = ImmutableList.copyOf(items);
    }
}
