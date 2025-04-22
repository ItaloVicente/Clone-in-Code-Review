
package org.eclipse.jgit.niofs.cluster;

import java.io.Serializable;
import java.util.function.Consumer;

public interface ClusterMessageService {

    void connect();

    <T> void createConsumer(DestinationType type
                            String channel
                            Class<T> clazz
                            Consumer<T> listener);

    void broadcast(DestinationType type
                   String channel
                   Serializable object);

    boolean isSystemClustered();

    void close();

    enum DestinationType {
        PubSub
        LoadBalancer
    }
}
