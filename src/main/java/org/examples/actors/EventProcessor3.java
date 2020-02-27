package org.examples.actors;

import io.vlingo.common.Completes;

public interface EventProcessor3 {
    Completes<Event3> process(String name);
}
