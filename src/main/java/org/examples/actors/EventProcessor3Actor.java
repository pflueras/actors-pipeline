package org.examples.actors;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;

public class EventProcessor3Actor extends Actor implements EventProcessor3 {
    @Override
    public Completes<Event3> process(String name) {
        System.out.println(getClass().getSimpleName() + ": " + name);
        Event3 result = new Event3(name);
        completes().with(result);

        return Completes.withSuccess(result);
    }
}
