package org.examples.actors;

import io.vlingo.common.Completes;

public interface EventProcessor2 {
    Completes<Event2> process(String name);
}
