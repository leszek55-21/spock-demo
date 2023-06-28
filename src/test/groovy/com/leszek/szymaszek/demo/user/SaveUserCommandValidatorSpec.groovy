package com.leszek.szymaszek.demo.user

import spock.lang.Specification
import spock.lang.Subject

import static com.leszek.szymaszek.demo.user.SaveUserCommandValidator.*

class SaveUserCommandValidatorSpec extends Specification {

    @Subject
    def saveUserCommandValidator = new SaveUserCommandValidator()

    def "should pass validation"() {
        given:
          final var saveUserCommand = new SaveUserCommand(firstName, lastName, age, new BigDecimal("199.99"))

        when:
          saveUserCommandValidator.validate(saveUserCommand)

        then:
          noExceptionThrown()

        where:
          firstName      | lastName       | age
          "a".repeat(10) | "a".repeat(2)  | 55    //75
          "a".repeat(3)  | "a".repeat(33) | 2     //101
          "a".repeat(4)  | "a".repeat(24) | 3     //99
    }

    def "should fail validation for firstName: #firstName, lastName: #lastName and age: #age"() {
        given:
          final var saveUserCommand = new SaveUserCommand(firstName, lastName, age, new BigDecimal("199.99"))

        when:
          saveUserCommandValidator.validate(saveUserCommand)

        then:
          def exception = thrown(ValidationException)
          exception.message == "Product of first name length, last name length summed with age cannot be $FORBIDDEN_RESULT"

        where:
          firstName << ["a".repeat(10), "a".repeat(3)]
          lastName  << ["a".repeat(5), "a".repeat(33)]
          age       << [50, 1]
    }
}
