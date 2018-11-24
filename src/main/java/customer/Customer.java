package customer;

import com.google.common.collect.ImmutableList;
import converters.ListToTripleConverter;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

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
                .max(comparing(Order::getPrice));

    }

    Triple<Order, Order, Order> findTop3OrdersByPrice() {
        return ListUtils.emptyIfNull(orders).stream()
                .filter(Order::hasPrice)
                .sorted(comparing(Order::getPrice, reverseOrder()))
                .limit(3)
                .collect(collectingAndThen(toList(), ListToTripleConverter::convert));
    }
}
