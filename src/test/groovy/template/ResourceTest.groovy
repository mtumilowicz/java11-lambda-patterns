package template

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-12-30.
 */
class ResourceTest extends Specification {
    def "test use"() {
        given:

        when:
        Resource.use("param", {resource -> resource.op1(); resource.op2()})
        
        then:
        1
    }
}
