
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;

import org.eclipse.jgit.transport.PublisherStream.Window;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class PublisherSession {
	public static class SubscribedRepository {
		private final Map<String

		private final String name;

		SubscribedRepository(String name) {
			this.name = name;
			subscribeSpecs = new HashMap<String
		}

		public synchronized boolean remove(SubscribeSpec s) {
			return (subscribeSpecs.remove(s.getRefName()) != null);
		}

		public synchronized boolean add(SubscribeSpec s) {
			return (subscribeSpecs.put(s.getRefName()
		}

		public Collection<SubscribeSpec> getSubscribeSpecs() {
			return Collections.unmodifiableCollection(subscribeSpecs.values());
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("SubscribedRepository[\n");
			for (SubscribeSpec s : getSubscribeSpecs())
				sb.append(s.toString()).append("\n");
			sb.append("]");
			return sb.toString();
		}
	}

	public interface SessionGenerator {
		public String generate();
	}

	private final Map<String

	private final String restartToken;

	private final Window<PublisherPush> pushStreamIterator;

	private ScheduledFuture<?> deleteTimer;

	PublisherSession(Window<PublisherPush> iterator
		restartToken = sessionId;
		state = new HashMap<String
		pushStreamIterator = iterator;
	}

	public String getKey() {
		return restartToken;
	}

	public synchronized void sync(Map<String
			throws IOException
			ServiceNotEnabledException {
		for (Entry<String
				commands.entrySet()) {
			String name = e.getKey();
			SubscribedRepository sr = state.get(name);
			if (sr == null) {
				sr = new SubscribedRepository(name);
				state.put(name
			}
			for (SubscribeCommand command : e.getValue()) {
				if (command.getCommand() == Command.SUBSCRIBE)
					sr.add(new SubscribeSpec(command.getSpec()));
			}
		}
	}

	public Collection<String> getSubscribedRepositoryNames() {
		return Collections.unmodifiableCollection(state.keySet());
	}

	public Collection<SubscribeSpec> getSubscriptions(String repoName) {
		SubscribedRepository r = state.get(repoName);
		if (r == null)
			return Collections.emptyList();
		return Collections.unmodifiableCollection(r.subscribeSpecs.values());
	}

	public void close() {
		pushStreamIterator.release();
	}

	public boolean rollbackUpdateStream(String pushId) {
		return pushStreamIterator.rollback(
				PublisherPush.equalityInstance(pushId));
	}

	public Window<PublisherPush> getStream() {
		return pushStreamIterator;
	}

	void setDeleteTimer(ScheduledFuture<?> timer) {
		deleteTimer = timer;
	}

	public boolean stopDeleteTimer() {
		if (deleteTimer == null)
			return true;
		return deleteTimer.cancel(false);
	}

	@Override
	public String toString() {
		return "PublisherSession[id=" + restartToken + "]";
	}
}
