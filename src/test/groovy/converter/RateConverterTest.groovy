package converter

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-26.
 */
class RateConverterTest extends Specification {
    def "test milesToKmConverter"() {
        given:
        def miles = 1000
        
        expect:
        RateConverter.milesToKmConverter().applyAsDouble(miles) == 1609
    }
}
