package com.leszek.szymaszek.demo.interaction

class Publisher {
    List<Subscriber> subscribers = new ArrayList<>()

    def publish(String message) {
        subscribers.forEach {it.receive(message)}
    }
}
