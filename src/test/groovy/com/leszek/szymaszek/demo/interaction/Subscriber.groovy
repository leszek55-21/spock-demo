package com.leszek.szymaszek.demo.interaction

class Subscriber {List<String> messages
    def receive(String message) {
        messages << message
        "OK"
    }
}
