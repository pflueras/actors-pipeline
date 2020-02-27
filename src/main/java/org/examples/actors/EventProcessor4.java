package org.examples.actors;

import io.vlingo.common.Completes;

public interface EventProcessor4 {
    Completes<Event4> process(String name);
}
