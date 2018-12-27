package customer;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

/**
 * Created by mtumilowicz on 2018-11-24.
 */
@Value
@Builder
public class Order {
    int id;
    BigDecimal price;

    boolean hasPrice() {
        return nonNull(price);
    }
}
