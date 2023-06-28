package com.leszek.szymaszek.demo.interaction

import org.hamcrest.Matchers
import spock.lang.Specification
import spock.lang.Subject

class PublisherSpec extends Specification {

    @Subject
    def publisher = new Publisher()
    Subscriber subscriber = Mock()
    Subscriber subscriber2 = Mock()

    def setup() {
        publisher.subscribers << subscriber
        publisher.subscribers << subscriber2
    }

    def "should publish message to all subscribers"() {
        given:
            def message = "Hello Spock"

        when:
            publisher.publish(message)

        then:
            1 * subscriber.receive(message)
            1 * subscriber2.receive(message)

            (1..3) * subscriber.receive(message) // between one and three calls (inclusive)
            (1.._) * subscriber.receive(message) // at least one call
            (_..3) * subscriber.receive(message) // at most three calls
            _ * subscriber.receive(message)      // any number of calls, including zero
            2 * _.receive(message)          // a call to any mock object
            1 * subscriber./r.*e/(message)  // a method whose name matches the given regular expression
                                            // (here: method name starts with 'r' and ends in 'e')
            1 * subscriber.receive(message)        // an argument that is equal to the String "hello"
            1 * subscriber.receive(message)       // an argument that is unequal to the String "hello"
            1 * subscriber.receive()               // the empty argument list (would never match in our example)
            1 * subscriber.receive(_)              // any single argument (including null)
            1 * subscriber.receive(*_)             // any argument list (including the empty argument list)
            1 * subscriber.receive(!null)          // any non-null argument
            1 * subscriber.receive(_ as String)    // any non-null argument that is-a String
            1 * subscriber.receive(Matchers.endsWith("lo")) // an argument matching the given Hamcrest matcher
                                                    // a String argument ending with "lo" in this case
            1 * subscriber.receive({ it.size() > 3 && it.contains('p') })
            // an argument that satisfies the given predicate, meaning that
            // code argument constraints need to return true of false
            // depending on whether they match or not
            // (here: message length is greater than 3 and contains the character a)
    }
}
