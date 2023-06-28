package com.leszek.szymaszek.demo.user

import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

class UserServiceSpec extends Specification {

    def userNameValidator
    def userRepository

    @Subject
    UserService userService

    def setup() {
        userNameValidator = Mock(SaveUserCommandValidator)
        userRepository = Mock(UserRepository)
        userService = new UserService(userNameValidator, userRepository)
    }

    def "should save user: #fName #lName and return save result"() {
        given:
          def givenAge = 48
          def givenHeight = new BigDecimal("1.85")
          def saveUserCommand = new SaveUserCommand(fName, lName, givenAge, new BigDecimal("1.85"))
          def user = new UserEntity(firstName: fName, lastName: lName, age: givenAge, height: givenHeight, createdDate: Instant.now())
          userRepository.save(_ as UserEntity) >> user
//            userRepository.save(_) >>> [user1, user2, user3] >> { throw new InternalError() } >> user1
//            This will return user1, user2, user3 for the first three invocations,
//            throw InternalError for the fourth invocation,
//            and return user1 again for any further invocation.

        when:
          def result = userService.saveUser(saveUserCommand)

        then:
          with(result) {
              it.firstName() == saveUserCommand.firstName()
              lastName == saveUserCommand.lastName()
              age() == saveUserCommand.age()
              height() == saveUserCommand.height()
              createdDate() != null
          }

        and:
          1 * userNameValidator.validate(saveUserCommand)

        where:
          fName  | lName
          "John" | "Doe"
          "Tom"  | "Johns"
    }

    def "should save user"() {
        given:
          def givenFirstName = "John"
          def givenLastName = "Doe"
          def givenAge = 48
          def givenHeight = new BigDecimal("1.85")
          def saveUserCommand = new SaveUserCommand(givenFirstName, givenLastName, givenAge, givenHeight)

        when:
          userService.saveUser2(saveUserCommand)

        then:
          1 * userRepository.save({
              it.firstName == givenFirstName
              it.lastName == givenLastName
              it.age == givenAge
              it.height == givenHeight
              it.createdDate != null
          })
    }
}
