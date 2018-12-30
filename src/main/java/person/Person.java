package person;

import lombok.Builder;
import lombok.Value;

import java.util.Comparator;

import static java.util.Comparator.*;

/**
 * Created by mtumilowicz on 2018-11-26.
 */
@Value
@Builder
class Person {
    static final Comparator<Person> NAME_SURNAME_COMPARATOR = comparing(Person::getName)
            .thenComparing(Person::getSurname, nullsFirst(naturalOrder()));

    String name;
    String surname;
}
