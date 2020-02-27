package org.examples.actors;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;

public class EventProcessor1Actor extends Actor implements EventProcessor1 {
    private final EventProcessor2 processor2;

    public EventProcessor1Actor(EventProcessor2 processor2) {
        this.processor2 = processor2;
    }

    @Override
    public Completes<Event1> process(String name) {
        return processor2.process(name)
                .andThen(e2 -> {
                    Event1 result = new Event1(e2.getName());
                    completesEventually().with(result);
                    return result;
                });
    }
}
