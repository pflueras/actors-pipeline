package org.examples.actors;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;

public class EventProcessor2Actor extends Actor implements EventProcessor2 {
    private final EventProcessor3 processor3;
    private final EventProcessor4 processor4;

    public EventProcessor2Actor(EventProcessor3 processor3, EventProcessor4 processor4) {
        this.processor3 = processor3;
        this.processor4 = processor4;
    }

    @Override
    public Completes<Event2> process(String name) {
        return processor3.process(name)
                .andThenTo(e3 -> processor4.process(e3.getName()))
                .andThen(e4 -> {
                    Event2 result = new Event2(e4.getName());
                    System.out.println(getClass().getSimpleName() + ".andThen(): " + e4.getName());
                    completesEventually().with(result);
                    return result;
                })
                .otherwise(ex -> {
                    Event2 result = Event2.empty();
                    System.out.println(getClass().getSimpleName() + ".otherwise(): " + result.getName());
                    completesEventually().with(result);
                    return result;
                });
    }
}
