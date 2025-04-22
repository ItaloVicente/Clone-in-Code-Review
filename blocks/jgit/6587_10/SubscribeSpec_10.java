
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.ConcurrentLinkedList.ConcurrentIterator;

public class PublisherSession {
	public class SubscribedRepository {
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

	private volatile PublisherClient client;

	private final Publisher publisher;

	private final ConcurrentIterator<PublisherPack> packStreamIterator;

	private List<PublisherPack> initialUpdates;

	private ScheduledFuture<?> deleteTimer;

	PublisherSession(Publisher p
		restartToken = generator.generate();
		state = new HashMap<String
		publisher = p;
		packStreamIterator = p.getPackStreamIterator();
	}

	public String getKey() {
		return restartToken;
	}

	public synchronized void sync(PublisherClient c) throws IOException
			ServiceNotAuthorizedException
		disconnect();
		client = c;

		Map<String
				c.getCommands());

		Map<String
				realCommands);

		for (Entry<String
				realCommands.entrySet()) {
			String name = e.getKey();
			Repository serverRepository = publisher.getRepository(
					c
			RefDatabase refDb = serverRepository.getRefDatabase();
			List<Ref> refList = new ArrayList<Ref>();
			for (Entry<SubscribeSpec
				if (e2.getValue() == Command.UNSUBSCRIBE)
					continue;
				SubscribeSpec s = e2.getKey();
				Collection<Ref> serverRefs;
				if (s.isWildcard()) {
					String refName = s.getRefName();
					refName = refName.substring(0
					serverRefs = refDb.getRefs(refName).values();
					if (serverRefs.isEmpty())
						continue;
					refList.addAll(serverRefs);
				} else {
					Ref r = refDb.getRef(s.getRefName());
					if (r == null)
						continue;
					refList.add(r);
				}
			}
			Map<String
			if (repoBaseRefs == null) {
				repoBaseRefs = new HashMap<String
				baseUpdateRefs.put(name
			}
			for (Ref r : refList) {
				if (!repoBaseRefs.containsKey(r.getName()))
					repoBaseRefs.put(r.getName()
			}
		}

		initialUpdates = new ArrayList<PublisherPack>();
		Map<String
				.getRefState();
		for (Entry<String
				baseUpdateRefs.entrySet()) {
			String repoName = e.getKey();
			Repository serverRepository = publisher.getRepository(
					c
			publisher.hookRepository(serverRepository

			List<ReceiveCommand> refUpdates = new ArrayList<ReceiveCommand>();

			Map<String
			for (Entry<String
				String refName = e2.getKey();
				ObjectId refValue = e2.getValue();
				ObjectId ourRef = clientRepoState.get(refName);
				if (ourRef == null)
					refUpdates.add(new ReceiveCommand(
							ObjectId.zeroId()
				else if (!refValue.equals(ourRef))
					refUpdates.add(new ReceiveCommand(
							ourRef
			}
			if (!refUpdates.isEmpty()) {
				initialUpdates.add(publisher.createInitialPack(
						refUpdates
			}
		}
	}

	private Map<String
			Map<String
		Map<String
				String
		for (Entry<String
			String repoName = e.getKey();
			SubscribedRepository repo = state.get(repoName);
			if (repo == null) {
				repo = new SubscribedRepository(repoName);
				state.put(repoName
			}
			Map<SubscribeSpec
					SubscribeSpec
			realCommands.put(repoName
			for (SubscribeCommand cmd : e.getValue()) {
				if (cmd.getCommand() == Command.SUBSCRIBE) {
					SubscribeSpec spec = new SubscribeSpec(cmd.getSpec());
					if (!repo.add(spec))
						realRepoCommands.put(spec
				} else if (cmd.getCommand() == Command.UNSUBSCRIBE) {
					SubscribeSpec spec = new SubscribeSpec(cmd.getSpec());
					if (repo.remove(spec))
						realRepoCommands.put(spec
				}
			}
		}
		return realCommands;
	}

	private Map<String
			Map<String
		Map<String
				String
		for (Iterator<PublisherPack> it = packStreamIterator.copy();
				it.hasNext();) {
			PublisherPack pk = it.next();
			Collection<ReceiveCommand> packCommands = pk.getCommands();

			if (!realCommands.containsKey(pk.getRepositoryName()))
				continue;
			boolean matchUnsubscribe = false;
			boolean matchSubscribe = false;
			Map<String
			Map<String
					pk.getRepositoryName());
			Map<SubscribeSpec
					pk.getRepositoryName());
			for (ReceiveCommand packCommand : packCommands) {
				for (Entry<SubscribeSpec
						changedCommands.entrySet()) {
					if (e.getKey().isMatch(packCommand.getRefName())) {
						if (e.getValue() == Command.SUBSCRIBE) {
							matchSubscribe = true;
							if (existingRefs != null && !existingRefs
									.containsKey(packCommand.getRefName()))
								matchingRefs.put(packCommand.getRefName()
										packCommand.getOldId());
						} else
							matchUnsubscribe = true;
					}
				}
			}
			if (matchUnsubscribe && matchSubscribe) {
			} else if (matchUnsubscribe) {
				pk.release();
			} else if (matchSubscribe) {
				if (!pk.incrementOpen())
					continue;
			}
			if (!matchingRefs.isEmpty()) {
				if (existingRefs == null)
					newSubscribeRefs.put(pk.getRepositoryName()
				else
					existingRefs.putAll(matchingRefs);
			}
		}
		return newSubscribeRefs;
	}

	public Collection<SubscribeSpec> getSubscriptions(String repoName) {
		SubscribedRepository r = state.get(repoName);
		if (r == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableCollection(r.subscribeSpecs.values());
	}

	public boolean isConnected() {
		PublisherClient pc = client;
		return (pc != null && !pc.isClosed());
	}

	public PublisherClient getClient() {
		return client;
	}

	public synchronized void disconnect() {
		PublisherClient pc = client;
		if (pc != null && !pc.isClosed()) {
			pc.close();
			client = null;
			startDeleteTimer();
		}
	}

	public void close() {
		disconnect();
		for (Iterator<PublisherPack> it = packStreamIterator.copy();
				it.hasNext();) {
			PublisherPack pk = it.next();
			SubscribedRepository sr = state.get(pk.getRepositoryName());
			if (sr == null)
				continue;
			if (pk.match(sr.getSubscribeSpecs()))
				pk.release();
		}
	}

	private boolean matchPack(PublisherPack pk) {
		SubscribedRepository sr = state.get(pk.getRepositoryName());
		return sr != null && pk.match(sr.getSubscribeSpecs());
	}

	public void rollbackUpdateStream() {
		packStreamIterator.reset();
	}

	public PublisherPack getNextUpdate(int heartbeatInterval)
			throws InterruptedException {
		if (!initialUpdates.isEmpty())
			return initialUpdates.remove(0);
		int waitTime = heartbeatInterval;
		while (waitTime > 0) {
			long startTime = System.currentTimeMillis();
			PublisherPack next;
			try {
				next = packStreamIterator.next(
						waitTime
			} catch (TimeoutException e) {
				next = null;
			}
			waitTime -= (System.currentTimeMillis() - startTime);
			if (next != null && matchPack(next)) {
				packStreamIterator.markBefore();
				return next;
			}
		}

		return null;
	}

	public PublisherPack peekNextUpdate() {
		return packStreamIterator.peek();
	}

	private void startDeleteTimer() {
		deleteTimer = publisher.startDeleteTimer(this);
	}

	public boolean stopDeleteTimer() {
		if (deleteTimer == null)
			return true;
		return deleteTimer.cancel(false);
	}

	@Override
	public String toString() {
		return "PublisherSession[id=" + restartToken + "
				+ isConnected() + "]";
	}
}
