package org.examples.actors;

public class Event2 {
    private final String name;

    public Event2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Event2 empty() {
        return new Event2("EMPTY");
    }
}
