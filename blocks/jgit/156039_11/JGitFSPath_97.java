package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.List;

import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;

public class JGitDiffAttributeViewImpl extends DiffAttributeViewImpl<JGitPathImpl> {

	private DiffAttributes attrs = null;
	private final String params;

	public JGitDiffAttributeViewImpl(final JGitPathImpl path
		super(path);
		this.params = params;
	}

	@Override
	public DiffAttributes readAttributes() throws IOException {
		if (attrs == null) {
			attrs = buildAttrs(path.getFileSystem()
		}
		return attrs;
	}

	@Override
	public Class<? extends BasicFileAttributeView>[] viewTypes() {
		return new Class[] { VersionAttributeView.class
				JGitDiffAttributeViewImpl.class };
	}

	private DiffAttributes buildAttrs(final JGitFileSystem fs
		final String[] branches = params.split("

	private String nodeId = UUID.randomUUID().toString();
	private Consumer<WatchEventsWrapper> eventsPublisher;
	private final ClusterMessageService clusterMessageService;

	public JGitEventsBroadcast(ClusterMessageService clusterMessageService
			Consumer<WatchEventsWrapper> eventsPublisher) {
		this.clusterMessageService = clusterMessageService;
		this.eventsPublisher = eventsPublisher;
		setupJMSConnection();
	}

	private void setupJMSConnection() {
		clusterMessageService.connect();
	}

	public void createWatchService(String topicName) {
		clusterMessageService.createConsumer(ClusterMessageService.DestinationType.PubSub
				WatchEventsWrapper.class
					if (!we.getNodeId().equals(nodeId)) {
						eventsPublisher.accept(we);
					}
				});
	}

	public synchronized void broadcast(String fsName
		clusterMessageService.broadcast(ClusterMessageService.DestinationType.PubSub
				new WatchEventsWrapper(nodeId
	}

	private String getChannelName(String fsName) {
		String channelName = DEFAULT_TOPIC;
		if (fsName.contains("/")) {
			channelName = fsName.substring(0
		}
		return channelName;
	}

	public void close() {
		clusterMessageService.close();
	}
}
