package com.leszek.szymaszek.demo.user;

import java.math.BigDecimal;

record SaveUserCommand (String firstName, String lastName, int age, BigDecimal height) {
}
