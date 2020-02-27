package org.examples.actors;

import io.vlingo.actors.World;
import org.junit.Assert;
import org.junit.Test;

public class ActorsPipelineTest {
    @Test
    public void testPipeline() {
        final World world = World.startWithDefaults("playground");
        final EventProcessor4 actor4 = world.actorFor(EventProcessor4.class, EventProcessor4Actor.class);
        final EventProcessor3 actor3 = world.actorFor(EventProcessor3.class, EventProcessor3Actor.class);
        final EventProcessor2 actor2 = world.actorFor(EventProcessor2.class, EventProcessor2Actor.class, actor3, actor4);
        final EventProcessor1 actor1 = world.actorFor(EventProcessor1.class, EventProcessor1Actor.class, actor2);
        String name = "Maria";

        Event1 e1_1 = actor1.process(name).await();
        Assert.assertEquals(name, e1_1.getName());

        Event1 e1_2 = actor1.process(name).await();
        Assert.assertEquals(name, e1_2.getName());
    }
}
