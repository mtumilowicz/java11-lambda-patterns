package customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import converters.ListToTripleConverter;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.time.Year;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.*;

/**
 * Created by mtumilowicz on 2018-11-24.
 */
@Value
@Builder
public class Customer {
    ImmutableList<Order> orders;
    ImmutableList<Expense> expenses;

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

    ImmutableMap<Year, Set<String>> yearTagsExpensesMap() {
        return ListUtils.emptyIfNull(expenses).stream()
                .collect(collectingAndThen(groupingBy(Expense::getYear, flatMapping(Expense::getTagsStream, toSet())),
                        ImmutableMap::copyOf)
                );
    }
}

@Value
@Builder
class Expense {
    Year year;
    ImmutableSet<String> tags;

    Stream<String> getTagsStream() {
        return SetUtils.emptyIfNull(tags).stream();
    }
}
