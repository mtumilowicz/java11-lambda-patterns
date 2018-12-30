package decorator


import spock.lang.Specification

import java.awt.*

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class ColorTransformersTest extends Specification {
    
    def "test brighten"() {
        given:
        def brighten = ColorTransformers.brighten(new Color(50, 50, 50), 10)

        expect:
        brighten.red == 60
        brighten.green == 60
        brighten.blue == 60
    }

    def "test negate"() {
        expect:
        ColorTransformers.negate(Color.BLACK) == Color.WHITE
    }
}
