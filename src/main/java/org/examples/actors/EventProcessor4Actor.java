package org.examples.actors;

import io.vlingo.actors.Actor;
import io.vlingo.common.Completes;

public class EventProcessor4Actor extends Actor implements EventProcessor4 {
    @Override
    public Completes<Event4> process(String name) {
        System.out.println(getClass().getSimpleName() + ": " + name);

        Event4 result = new Event4(name);
        completes().with(result);

        return Completes.withSuccess(result);
    }
}
