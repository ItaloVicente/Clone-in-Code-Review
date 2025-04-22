
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PublisherSession.SessionGenerator;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.util.ConcurrentLinkedList;
import org.eclipse.jgit.util.ConcurrentLinkedList.ConcurrentIterator;

public class Publisher {
	private static final int DEFAULT_SESSION_LIFETIME = 60 * 60;

	class PublisherEventProcessor {
		LinkedHashSet<PublisherSession> clients = new LinkedHashSet<PublisherSession>();

		public synchronized void addClient(PublisherSession c) {
			clients.add(c);
		}

		public synchronized void removeClient(PublisherSession c) {
			clients.remove(c);
		}

		public synchronized Map<Set<ReceiveCommand>
				String name
			Map<Set<ReceiveCommand>

			for (PublisherSession c : clients) {
				Collection<SubscribeSpec> clientSpecs = c.getSubscriptions(
						name);
				if (!clientSpecs.isEmpty()) {
					Set<ReceiveCommand> matchedUpdates = new LinkedHashSet<
							ReceiveCommand>();
					for (ReceiveCommand r : updates) {
						for (SubscribeSpec f : clientSpecs) {
							if (f.isMatch(r.getRefName())) {
								matchedUpdates.add(r);
								break;
							}
						}
					}
					if (matchedUpdates.size() > 0) {
						if (!groups.containsKey(matchedUpdates))
							groups.put(matchedUpdates
									new ArrayList<PublisherSession>());
						groups.get(matchedUpdates).add(c);
					}
				}
			}
			return groups;
		}
	}

	private final PostReceiveHook postReceiveHook = new PostReceiveHook() {
		public void onPostReceive(
				ReceivePack rp
			List<ReceiveCommand> updates = new ArrayList<ReceiveCommand>();
			for (ReceiveCommand c : commands) {
				if (c.getResult() != ReceiveCommand.Result.OK)
					continue;
				updates.add(c);
			}
			try {
				onPush(rp.db
			} catch (NotSupportedException e) {
				e.printStackTrace();
			}
		}
	};

	private final PublisherPackFactory packFactory;

	private final PublisherEventProcessor processor;

	private final Map<String

	private final PublisherReverseResolver repositoryNameLookup;

	private final ConcurrentLinkedList<PublisherPack> packStream;

	private final ScheduledThreadPoolExecutor sessionDeleter;

	private final SessionGenerator generator;

	private int sessionLifetime;

	public Publisher(PublisherReverseResolver repositoryNameLookup
			PublisherPackFactory packFactory
		processor = new PublisherEventProcessor();
		clientStates = new HashMap<String
		packStream = new ConcurrentLinkedList<PublisherPack>();
		sessionDeleter = new ScheduledThreadPoolExecutor(1);
		sessionLifetime = DEFAULT_SESSION_LIFETIME;
		this.repositoryNameLookup = repositoryNameLookup;
		this.packFactory = packFactory;
		this.generator = generator;
	}

	public void setSessionLifetime(int lifetime) {
		sessionLifetime = lifetime;
	}

	public synchronized PublisherSession connectClient(PublisherClient c)
			throws ServiceNotAuthorizedException
		PublisherSession state;
		if (c.getRestartToken() != null) {
			if (!clientStates.containsKey(c.getRestartToken()))
				return null;
			state = clientStates.get(c.getRestartToken());
			if (!state.stopDeleteTimer())
				return null;
		} else {
			state = new PublisherSession(this
			clientStates.put(state.getKey()
		}

		boolean failedSyncing = false;
		try {
			state.sync(c);
			processor.addClient(state);
			return state;
		} catch (IOException e) {
			failedSyncing = true;
			e.printStackTrace();
		} catch (ServiceNotAuthorizedException e) {
			failedSyncing = true;
			throw e;
		} catch (ServiceNotEnabledException e) {
			failedSyncing = true;
			throw e;
		} finally {
			if (failedSyncing) {
				state.disconnect();
				processor.removeClient(state);
			}
		}
		return null;
	}

	public synchronized void removeSession(PublisherSession session) {
		clientStates.remove(session.getKey());
		session.close();
	}

	public PostReceiveHook getHook() {
		return postReceiveHook;
	}

	public void hookRepository(Repository r
		repositoryNameLookup.register(r
	}

	private String getName(Repository r) throws NotSupportedException {
		return repositoryNameLookup.find(r);
	}

	public synchronized void onPush(
			Repository db
		String dbName = getName(db);
		Map<Set<ReceiveCommand>
				.getAffectedClients(dbName
		for (Map.Entry<Set<ReceiveCommand>
				updateGroups.entrySet()) {
			List<PublisherSession> updateGroup = e.getValue();
			Map<Set<SubscribeSpec>
					= new HashMap<Set<SubscribeSpec>

			if (updateGroup.size() == 0)
				break;
			for (PublisherSession s : updateGroup) {
				Set<SubscribeSpec> specSet = new HashSet<SubscribeSpec>();
				specSet.addAll(s.getSubscriptions(dbName));
				List<PublisherSession> sessionGroup = existingObjectGroups.get(
						specSet);
				if (sessionGroup == null) {
					sessionGroup = new ArrayList<PublisherSession>();
					sessionGroup.add(s);
					existingObjectGroups.put(specSet
				} else
					sessionGroup.add(s);
			}

			for (Map.Entry<Set<SubscribeSpec>
					existingObjectGroups.entrySet()) {
				List<PublisherSession> sessionList = e2.getValue();
				Set<SubscribeSpec> sessionSpecs = e2.getKey();
				try {
					PublisherPack pk = packFactory.buildPack(sessionList.size()
							dbName
					packStream.put(pk);
				} catch (IOException ex) {
				}
			}
		}
	}

	public Repository getRepository(PublisherClient c
			throws RepositoryNotFoundException
			ServiceNotAuthorizedException
		return c.openRepository(name);
	}

	public ConcurrentIterator<PublisherPack> getPackStreamIterator() {
		return packStream.getTailIterator();
	}

	public PublisherPack createInitialPack(List<ReceiveCommand> refUpdates
			Repository serverRepository
		List<SubscribeSpec> emptySpecs = Collections.emptyList();
		return packFactory.buildPack(
				1
	}

	public ScheduledFuture<?> startDeleteTimer(
			final PublisherSession session) {
		return sessionDeleter.schedule(new Callable<Void>() {
			public Void call() throws Exception {
				removeSession(session);
				return null;
			}
		}
	}
}
