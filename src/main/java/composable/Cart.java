package composable;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
@Value
@Builder
class Cart {
    ImmutableList<Item> items;
}
