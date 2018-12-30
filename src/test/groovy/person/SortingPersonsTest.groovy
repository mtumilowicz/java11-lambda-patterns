package person

import spock.lang.Specification

import static java.util.stream.Collectors.toList

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class SortingPersonsTest extends Specification {

    def "sort by name then by nullable surname - nulls first"() {
        given:
        def B_B = Person.builder().name("B").surname("B_B").build()
        def C_A = Person.builder().name("C").surname("C_A").build()
        def A = Person.builder().name("A").surname("A").build()
        def B_A = Person.builder().name("B").surname("B_A").build()
        def C_null = Person.builder().name("C").surname(null).build()
        def C_null2 = Person.builder().name("C").surname(null).build()

        when:
        def list = List.of(B_B, C_A, A, B_A, C_null, C_null2)
                .stream()
                .sorted(Person.NAME_SURNAME_COMPARATOR)
                .collect(toList())

        then:
        list == [A, B_A, B_B, C_null, C_null2, C_A]
    }
}