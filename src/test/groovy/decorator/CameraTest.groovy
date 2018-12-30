package decorator

import spock.lang.Specification

import java.awt.*

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class CameraTest extends Specification {

    def "test withFilter"() {
        given:
        def camera = new Camera().withFilter({ ColorTransformers.negate(it) })

        expect:
        camera.snap(Color.WHITE) == Color.BLACK
    }

    def "test withFilter - negate then brighten"() {
        given:
        def camera = new Camera().withFilter({ ColorTransformers.negate(it) })
                .withFilter({ ColorTransformers.brighten(it, 20) })

        expect:
        camera.snap(new Color(100, 100, 100)) == new Color(175, 175, 175)
    }

    def "test snap - no filter"() {
        given:
        def camera = new Camera()

        expect:
        camera.snap(Color.BLACK) == Color.BLACK
    }
}
