package decorator

import spock.lang.Specification

import java.awt.*

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class CameraTest extends Specification {

    def "test withFilter"() {
        given:
        def camera = new Camera().withFilter({ color -> ColorTransformers.negate(color) })

        expect:
        camera.snap(Color.WHITE) == Color.BLACK
    }

    def "test snap - no filter"() {
        given:
        def camera = new Camera()

        expect:
        camera.snap(Color.BLACK) == Color.BLACK
    }
}
