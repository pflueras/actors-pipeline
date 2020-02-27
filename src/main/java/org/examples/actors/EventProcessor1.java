package org.examples.actors;

import io.vlingo.common.Completes;

public interface EventProcessor1 {
    Completes<Event1> process(String name);
}
