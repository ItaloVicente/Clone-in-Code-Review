
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PublisherSession.SessionGenerator;
import org.eclipse.jgit.transport.PublisherStream.Window;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class Publisher {
	private static final int DEFAULT_SESSION_LIFETIME = 60 * 60;

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

	private final Map<String

	private final PublisherReverseResolver repositoryNameLookup;

	private final PublisherStream<PublisherPush> pushStream;

	private final ScheduledThreadPoolExecutor sessionDeleter;

	private final SessionGenerator generator;

	private int sessionLifetime;

	private long pushCount;

	public Publisher(PublisherReverseResolver repositoryNameLookup
			PublisherPackFactory packFactory
		sessions = new HashMap<String
		pushStream = new PublisherStream<PublisherPush>();
		sessionDeleter = new ScheduledThreadPoolExecutor(1);
		sessionLifetime = DEFAULT_SESSION_LIFETIME;
		this.repositoryNameLookup = repositoryNameLookup;
		this.packFactory = packFactory;
		this.generator = generator;
	}

	public void setSessionLifetime(int lifetime) {
		sessionLifetime = lifetime;
	}

	public synchronized PublisherSession newSession() {
		Window<PublisherPush> it = pushStream.newIterator(2);
		PublisherSession session = new PublisherSession(it
				.generate());
		sessions.put(session.getKey()
		return session;
	}

	public synchronized PublisherSession reconnectSession(String sessionId)
			throws ServiceNotAuthorizedException
		PublisherSession state = sessions.get(sessionId);
		if (state == null || !state.stopDeleteTimer())
			return null;
		return state;
	}

	public synchronized void removeSession(PublisherSession session) {
		sessions.remove(session.getKey());
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
		if (dbName == null)
			return;
		pushStream.add(new PublisherPush(
				dbName
	}

	public PublisherPackFactory getPackFactory() {
		return packFactory;
	}

	public void startDeleteTimer(
			final PublisherSession session) {
		session.setDeleteTimer(sessionDeleter.schedule(new Callable<Void>() {
			public Void call() throws Exception {
				removeSession(session);
				return null;
			}
		}
	}
}
