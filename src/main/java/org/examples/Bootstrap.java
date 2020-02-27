package org.examples;

import io.vlingo.actors.Stage;
import io.vlingo.actors.World;
import io.vlingo.common.identity.IdentityGeneratorType;
import io.vlingo.examples.actors.*;
import org.examples.actors.*;
import org.examples.resource.EventResource;
import io.vlingo.http.resource.Configuration;
import io.vlingo.http.resource.Resources;
import io.vlingo.http.resource.Server;
import io.vlingo.lattice.grid.Grid;
import io.vlingo.lattice.grid.GridAddressFactory;

public class Bootstrap {
    private static Bootstrap instance;
    private final int port = 9105;
    private final String stageName = "vlingo-actors-stage-pipeline";
    private final Server server;
    private final World world;

    public Bootstrap() throws Exception {
        world = World.startWithDefaults("vlingo-actors-pipeline");
        world.stageNamed(stageName, Grid.class, new GridAddressFactory(IdentityGeneratorType.RANDOM));
        final Stage stage = world.stageNamed(stageName);

        final EventProcessor4 actor4 = stage.actorFor(EventProcessor4.class, EventProcessor4Actor.class);
        final EventProcessor3 actor3 = stage.actorFor(EventProcessor3.class, EventProcessor3Actor.class);
        final EventProcessor2 actor2 = stage.actorFor(EventProcessor2.class, EventProcessor2Actor.class, actor3, actor4);
        final EventProcessor1 actor1 = stage.actorFor(EventProcessor1.class, EventProcessor1Actor.class, actor2);

        Resources allResources = Resources.are(new EventResource(actor1).routes());
        server = Server.startWith(world.stage(),
                allResources,
                port,
                Configuration.Sizing.define()
                        .withDispatcherPoolSize(2)
                        .withMaxBufferPoolSize(100)
                        .withMaxMessageSize(4096),
                Configuration.Timing.define());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (instance != null) {
                instance.server.stop();

                System.out.println("\n");
                System.out.println("=========================");
                System.out.println("Stopping vlingo-actors-pipeline.");
                System.out.println("=========================");
            }
        }));
    }

    static Bootstrap instance() throws Exception {
        if (instance == null) {
            instance = new Bootstrap();
        }
        return instance;
    }

    public static void main(final String[] args) throws Exception {
        System.out.println("=========================");
        System.out.println("service: vlingo-actors-pipeline.");
        System.out.println("=========================");
        Bootstrap.instance();
    }
}
