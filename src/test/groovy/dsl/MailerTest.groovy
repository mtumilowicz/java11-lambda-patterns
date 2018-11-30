package dsl

import spock.lang.Specification

/**
 * Created by mtumilowicz on 2018-11-30.
 */
class MailerTest extends Specification {
    def "_"() {
        // you don't have access to the object - you cannot reuse it later
        // there is NO Mailer object
        Mailer.send({
            it.from("mtumilowicz01@gmail.com")
                    .to("abc@o2.pl")
        })

        expect:
        1 == 1
    }
}
