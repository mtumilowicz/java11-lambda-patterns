package property

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-24.
 */
class PropertyHandlerTest extends Specification {
//    def "test init"() {
//        given:
//
//        when:
//        // TODO implement stimulus
//        then:
//        // TODO implement assertions
//    }

    def "test get - key exists"() {
        given:
        def propertyHandler = new PropertyHandler(ImmutableMap.of(
                "user.name", "michal"
        ))
        
        expect:
        propertyHandler.get("user.name") == Optional.of("michal")
    }

    def "test get - does not exist"() {
        given:
        def propertyHandler = new PropertyHandler(ImmutableMap.of(
        ))

        expect:
        propertyHandler.get("user.name") == Optional.empty()
    }

    def "test allPropertiesFor"() {
        given:
        def propertyHandler = new PropertyHandler(ImmutableMap.of(
                "user.name", "michal",
                "user.surname", "tumilowicz",
                "user.login", "mtumilowicz"
        ))

        when:
        def properties = propertyHandler.propertiesOf(ImmutableList.of("user.name", "user.surname", "user.login", "NOT-EXISTS"))

        then:
        properties == "michal,tumilowicz,mtumilowicz"
    }
}
