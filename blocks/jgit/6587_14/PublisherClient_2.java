
package org.eclipse.jgit.transport;

import java.io.IOException;
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

public class Publisher implements PostReceiveHook {
	public static class PublisherException extends IOException {
		private static final long serialVersionUID = 1L;

		public PublisherException(String message) {
			super(message);
		}

		public PublisherException(String message
			super(message);
			initCause(cause);
		}
	}

	private static final int DEFAULT_SESSION_GRACE_PERIOD = 60 * 60;

	private static final int DEFAULT_SESSION_ROLLBACK_COUNT = 2;

	private final PublisherPackFactory packFactory;

	private final Map<String

	private final PublisherReverseResolver repositoryNameLookup;

	private final PublisherStream pushStream;

	private final ScheduledThreadPoolExecutor sessionDeleter;

	private final SessionGenerator generator;

	private int sessionLifetime;

	private int sessionRollbackCount;

	private long pushCount;

	private volatile boolean closed;

	public Publisher(PublisherReverseResolver repositoryNameLookup
			PublisherPackFactory packFactory
		sessions = new HashMap<String
		pushStream = new PublisherStream();
		sessionDeleter = new ScheduledThreadPoolExecutor(1);
		sessionLifetime = DEFAULT_SESSION_GRACE_PERIOD;
		sessionRollbackCount = DEFAULT_SESSION_ROLLBACK_COUNT;
		this.repositoryNameLookup = repositoryNameLookup;
		this.packFactory = packFactory;
		this.generator = generator;
	}

	public void setSessionLifetime(int lifetime) {
		sessionLifetime = lifetime;
	}

	public void setSessionRollbackCount(int count) {
		sessionRollbackCount = count;
	}

	public synchronized PublisherSession newSession() {
		Window it = pushStream.newWindow(sessionRollbackCount);
		PublisherSession session = new PublisherSession(it
				.generate());
		sessions.put(session.getKey()
		return session;
	}

	public synchronized PublisherSession reconnectSession(String sessionId)
			throws ServiceNotAuthorizedException
		PublisherSession state = sessions.get(sessionId);
		if (state == null || !state.cancelDeleteTimer())
			return null;
		return state;
	}

	public synchronized void removeSession(PublisherSession session) {
		sessions.remove(session.getKey());
		try {
			session.close();
		} catch (PublisherException e) {
			close();
			e.printStackTrace();
		}
	}

	public void onPostReceive(
			ReceivePack rp
		if (isClosed())
			return;
		List<ReceiveCommand> updates = new ArrayList<ReceiveCommand>(
				commands.size());
		for (ReceiveCommand c : commands) {
			if (c.getResult() != ReceiveCommand.Result.OK)
				continue;
			updates.add(c);
		}
		try {
			onPush(rp.db
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (PublisherException e) {
			close();
			e.printStackTrace();
		}
	}

	public void hookRepository(Repository r
			throws NotSupportedException {
		repositoryNameLookup.register(r
	}

	public synchronized void onPush(
			Repository db
			throws NotSupportedException
		String dbName = repositoryNameLookup.find(db);
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

	public void close() {
		closed = true;
	}

	public boolean isClosed() {
		return closed;
	}
}
