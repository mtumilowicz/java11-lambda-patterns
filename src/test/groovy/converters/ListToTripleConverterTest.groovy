package converters

import org.apache.commons.lang3.tuple.ImmutableTriple
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-24.
 */
class ListToTripleConverterTest extends Specification {
    
    def "test convert - null list"() {
        expect:
        ListToTripleConverter.convert(null) == ImmutableTriple.nullTriple()
    }

    def "test convert - [1]"() {
        expect:
        ListToTripleConverter.convert([1]) == ImmutableTriple.of(1, null, null)
    }

    def "test convert - [1, 2]"() {
        expect:
        ListToTripleConverter.convert([1, 2]) == ImmutableTriple.of(1, 2, null)
    }

    def "test convert - [1, 2, 3]"() {
        expect:
        ListToTripleConverter.convert([1, 2, 3]) == ImmutableTriple.of(1, 2, 3)
    }

    def "test convert - [1, 2, 3, 4]"() {
        expect:
        ListToTripleConverter.convert([1, 2, 3]) == ImmutableTriple.of(1, 2, 3)
    }
}
