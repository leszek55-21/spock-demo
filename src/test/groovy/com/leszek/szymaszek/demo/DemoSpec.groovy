package com.leszek.szymaszek.demo

import com.leszek.szymaszek.demo.user.UserEntity
import spock.lang.Specification

class DemoSpec extends Specification {

    def "should calculate power"() {
        when:
            def result = base**exponent

        then:
            result == expectedResult

        where:
            //Data Table
            base | exponent || expectedResult
            1    | 0        || 1
            2    | 0        || 1
            2    | 3        || 8
            4    | -1       || 0.25
    }

    def "should sum #a and #b and return #expectedResult; [#iterationIndex]; [#dataVariablesWithIndex]"() {
        //special tokens
        when:
            def result = a + b

        then:
            result == expectedResult

        where:
            //Data Pipes
            a << [2, 5, -10]
            b << [0, 5, -2]
            expectedResult << [2, 10, -12]
    }

    def "should throw IndexOutOfBoundsException"() {
        given:
            def list = new ArrayList()

        when:
            list.get(0)

        then:
            thrown(IndexOutOfBoundsException)
    }

    def "should allow adding null to list"() {
        given:
            def list = new ArrayList()

        when:
            list << null    //list.add()

        then:
            notThrown(NullPointerException)
    }

    def "should check collection lenient match"() {
        given: "collection of integers"
            def integers = [2, 4]

        expect:
            integers =~ [2, 2, 4, 4, 4]
    }

    def "should check collection strict match"() {
        given: "collection of integers"
            def integers = [2, 4, 1]

        expect:
            integers ==~ [1, 2, 4]
    }

    def "should check user using 'with'"() {
        when:
            def user = new UserEntity(
                firstName: "John",
                lastName: "Doe",
                age: 10,
                height: new BigDecimal("2.03")
            )

        then:
            with(user) {
                it.firstName == "John"
                it.lastName == "Johns"
                it.age == 12
                it.height == new BigDecimal("2.03")
            }
    }

    def "should check user using 'verifyAll'"() {
        when:
            def user = new UserEntity(
                firstName: "John",
                lastName: "Doe",
                age: 10,
                height: new BigDecimal("2.03")
            )

        then:
            verifyAll (user) {
                it.firstName == "John"
                it.lastName == "Johns"
                it.age == 12
                it.height == new BigDecimal("2.03")
            }
    }

    def "should verify all conditions"() {
        expect:
            verifyAll{
                1 == 1
                "str" == "STR".toLowerCase()
                new BigDecimal("2.03") == new BigDecimal("2.03")
            }
    }
}
