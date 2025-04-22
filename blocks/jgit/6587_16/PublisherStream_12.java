
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import org.eclipse.jgit.transport.PublisherStream.Window;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class PublisherSession {
	public static class SubscribedRepository {
		private final Set<SubscribeSpec> subscribeSpecs;

		private final String name;

		private SubscribedRepository(String name) {
			this.name = name;
			subscribeSpecs = new HashSet<SubscribeSpec>();
		}

		private boolean add(SubscribeSpec s) {
			return !subscribeSpecs.add(s);
		}

		public Set<SubscribeSpec> getSubscribeSpecs() {
			return Collections.unmodifiableSet(subscribeSpecs);
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

	private final Window pushStreamIterator;

	private ScheduledFuture<?> deleteTimer;

	PublisherSession(Window iterator
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

	public synchronized Set<String> getSubscribedRepositoryNames() {
		return Collections.unmodifiableSet(state.keySet());
	}

	public synchronized Set<SubscribeSpec> getSubscriptions(String repoName) {
		SubscribedRepository r = state.get(repoName);
		if (r == null)
			return Collections.emptySet();
		return r.getSubscribeSpecs();
	}

	public void close() throws PublisherException {
		pushStreamIterator.release();
	}

	public PublisherStream.Window getStream() {
		return pushStreamIterator;
	}

	void setDeleteTimer(ScheduledFuture<?> timer) {
		deleteTimer = timer;
	}

	public boolean cancelDeleteTimer() {
		if (deleteTimer == null)
			return true;
		boolean result = deleteTimer.cancel(false);
		deleteTimer = null;
		return result;
	}

	@Override
	public String toString() {
		return "PublisherSession[id=" + restartToken + "]";
	}
}
