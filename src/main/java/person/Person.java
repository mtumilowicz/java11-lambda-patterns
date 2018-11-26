package person;

import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
@Value
@Builder
class Person {
    String name;
    String surname;
}
