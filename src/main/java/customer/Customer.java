package customer;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.ListUtils;

import java.util.Comparator;
import java.util.Optional;

/**
 * Created by mtumilowicz on 2018-11-24.
 */
@Value
@Builder
public class Customer {
    ImmutableList<Order> orders;
    
    Optional<Order> findOrderWithMaxPrice() {
        return ListUtils.emptyIfNull(orders).stream()
                .filter(Order::hasPrice)
                .max(Comparator.comparing(Order::getPrice));
                
    }
}
