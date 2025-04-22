package org.eclipse.jgit.niofs.cluster;

import java.io.Serializable;
import java.util.function.Consumer;

public interface ClusterMessageService {

	void connect();

	<T> void createConsumer(DestinationType type

	void broadcast(DestinationType type

	boolean isSystemClustered();

	void close();

	enum DestinationType {
		PubSub
	}
}
