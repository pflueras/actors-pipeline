package org.examples.resource;

import io.vlingo.common.Completes;
import org.examples.actors.EventProcessor1;
import io.vlingo.http.Response;
import io.vlingo.http.resource.Resource;

import static io.vlingo.common.serialization.JsonSerialization.serialized;
import static io.vlingo.http.Response.Status.Ok;
import static io.vlingo.http.resource.ResourceBuilder.get;
import static io.vlingo.http.resource.ResourceBuilder.resource;

public class EventResource {
    private final EventProcessor1 processor1;

    public EventResource(EventProcessor1 processor1) {
        this.processor1 = processor1;
    }

    private Completes<Response> query(final String name) {
        return processor1.process(name)
                .andThenTo(event1 -> Completes.withSuccess(Response.of(Ok, serialized(event1))));
    }

    public Resource<?> routes() {
        return resource("Event API",
                get("/events/{name}")
                        .param(String.class)
                        .handle(this::query));
    }
}
