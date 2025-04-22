package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JGitFileSystemsEventsManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemsEventsManager.class);

	private final Map<String

	private final ClusterMessageService clusterMessageService;

	JGitEventsBroadcast jGitEventsBroadcast;

	public JGitFileSystemsEventsManager() {
		clusterMessageService = getClusterMessageService();

		if (clusterMessageService.isSystemClustered()) {
			setupJGitEventsBroadcast();
		}
	}

	ClusterMessageService getClusterMessageService() {
		return new ClusterMessageService() {
			@Override
			public void connect() {

			}

			@Override
			public <T> void createConsumer(DestinationType type

			}

			@Override
			public void broadcast(DestinationType type

			}

			@Override
			public boolean isSystemClustered() {
				return false;
			}

			@Override
			public void close() {

			}
		};
	}

	void setupJGitEventsBroadcast() {
		jGitEventsBroadcast = new JGitEventsBroadcast(clusterMessageService
				w -> publishEvents(w.getFsName()
	}

	public WatchService newWatchService(String fsName) throws UnsupportedOperationException
		fsWatchServices.putIfAbsent(fsName

		if (jGitEventsBroadcast != null) {
			jGitEventsBroadcast.createWatchService(fsName);
		}

		return fsWatchServices.get(fsName).newWatchService(fsName);
	}

	JGitFileSystemWatchServices createFSWatchServicesManager() {
		return new JGitFileSystemWatchServices();
	}

	public void publishEvents(String fsName

		publishEvents(fsName
	}

	public void publishEvents(String fsName

		JGitFileSystemWatchServices watchService = fsWatchServices.get(fsName);

		if (watchService == null) {
			return;
		}

		watchService.publishEvents(watchable

		if (shouldIBroadcast(broadcastEvents)) {
			jGitEventsBroadcast.broadcast(fsName
		}
	}

	private boolean shouldIBroadcast(boolean broadcastEvents) {
		return broadcastEvents && jGitEventsBroadcast != null;
	}

	public void close(String name) {

		JGitFileSystemWatchServices watchService = fsWatchServices.get(name);

		if (watchService != null) {
			try {
				watchService.close();
			} catch (final Exception ex) {
				LOGGER.error("Can't close watch service [" + toString() + "]"
			}
		}
	}

	public void shutdown() {
		fsWatchServices.keySet().forEach(key -> this.close(key));

		if (jGitEventsBroadcast != null) {
			jGitEventsBroadcast.close();
		}
	}

	JGitEventsBroadcast getjGitEventsBroadcast() {
		return jGitEventsBroadcast;
	}

	Map<String
		return fsWatchServices;
	}
}
