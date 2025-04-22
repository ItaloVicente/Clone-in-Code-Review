
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.SubscribeCommand.Command.SUBSCRIBE;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PublisherStream.Window;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public abstract class PublisherClient {
	private static final int HEARTBEAT_INTERVAL = 10000;

	private final Publisher publisher;

	private PublisherSession session;

	private Map<String

	private Map<String

	private PacketLineIn in;

	private OutputStream out;

	private String restartToken;

	private String lastPackId;

	private volatile boolean closed;

	private Thread consumeThread;

	public PublisherClient(Publisher p) {
		publisher = p;
		commands = new HashMap<String
		refState = new HashMap<String
	}

	public void subscribe(
			InputStream myIn
			throws IOException
			ServiceNotEnabledException {
		this.in = new PacketLineIn(myIn);
		this.out = new BufferedOutputStream(myOut);

		if (isAdvertisement())
			doAdvertisement();
		else
			doSubscribe();
	}

	private boolean isAdvertisement() throws TransportException
		String line = in.readString();
		if (line.equals("advertisement"))
			return true;
		if (line.equals("subscribe"))
			return false;
		throw new TransportException(MessageFormat.format(
				JGitText.get().expectedGot
	}

	private void doAdvertisement()
			throws IOException
		PacketLineOut pktLineOut = new PacketLineOut(out);
		try {
			String line;
			while ((line = in.readString()) != PacketLineIn.END) {
				if (!line.startsWith("repositoryaccess "))
					throw new TransportException(MessageFormat.format(
							JGitText.get().expectedGot
							line));
				Repository r = null;
				try {
					r = openRepository(line.substring(
							"repositoryaccess ".length()));
				} finally {
					if (r != null)
						r.close();
				}
			}
			pktLineOut.writeString("ACK");
		} catch (TransportException e) {
			pktLineOut.writeString("ERR " + e.getMessage());
		} catch (ServiceNotEnabledException e) {
			pktLineOut.writeString("ERR " + e.getMessage());
		} finally {
			pktLineOut.flush();
		}
	}

	private void doSubscribe() throws TransportException
			ServiceNotAuthorizedException
		readHeaders();
		readSubscribeCommands();

		PacketLineOut pktLineOut = new PacketLineOut(out);
		if (commands.size() > 0 && restartToken != null) {
			pktLineOut.writeString("reconnect");
			pktLineOut.end();
			return;
		}

		if (restartToken != null) {
			session = publisher.reconnectSession(restartToken);
			if (session == null) {
				pktLineOut.writeString("reconnect");
				pktLineOut.end();
				return;
			}
			if (lastPackId != null) {
				if (!session.rollbackUpdateStream(lastPackId)) {
					pktLineOut.writeString("reconnect");
					pktLineOut.end();
					return;
				}
			}
		} else {
			session = publisher.newSession();
			session.sync(commands);
			generateInitialPacks();
		}

		pktLineOut.writeString("ACK");
		pktLineOut.writeString("restart-token " + session.getKey());
		pktLineOut.writeString(
				"heartbeat-interval " + HEARTBEAT_INTERVAL / 1000);
		pktLineOut.flush();

		consumeThread = Thread.currentThread();
		String oldThreadName = consumeThread.getName();
		consumeThread.setName("PubSub Consumer " + session.getKey());
		try {
			Window<PublisherPush> it = session.getStream();
			while (true) {
				if (Thread.interrupted())
					throw new InterruptedException();
				int waitTime = HEARTBEAT_INTERVAL;
				PublisherPush push = null;
				PublisherPack pack = null;
				while (waitTime > 0) {
					long startTime = System.currentTimeMillis();
					push = it.next(waitTime
					waitTime -= (System.currentTimeMillis() - startTime);
					if (push == null)
						break;
					pack = push.get(this);
					if (pack == null)
					it.mark();
					break;
				}
				if (closed)
					throw new EOFException();
				if (push == null || pack == null) {
					pktLineOut.writeString("heartbeat");
					pktLineOut.flush();
				} else {
					pktLineOut.writeString("update "
							+ push.getRepositoryName());
					pack.writeToStream(out);
					pktLineOut.writeString("pack-id " + push.getPushId());
					pktLineOut.flush();
				}
			}
		} catch (InterruptedException e) {
		} finally {
			consumeThread.setName(oldThreadName);
			consumeThread = null;
			publisher.startDeleteTimer(session);
		}
	}

	private void generateInitialPacks() throws RepositoryNotFoundException
			ServiceMayNotContinueException
			ServiceNotEnabledException
		Window<PublisherPush> it = session.getStream();
		int idCounter = 0;
		for (String name : session.getSubscribedRepositoryNames()) {
			List<ReceiveCommand> updateCommands = new ArrayList<
					ReceiveCommand>();
			Map<String
			if (clientState == null)
				clientState = Collections.emptyMap();
			Repository r = openRepository(name);
			publisher.hookRepository(r
			RefDatabase refDb = r.getRefDatabase();
			for (SubscribeSpec spec : session.getSubscriptions(name)) {
				Collection<Ref> matchedServerRefs;
				if (spec.isWildcard()) {
					String ref = spec.getRefName();
					matchedServerRefs = refDb.getRefs(ref.substring(0
							ref.length() - 1)).values();
				} else {
					Ref ref = refDb.getRef(spec.getRefName());
					if (ref == null)
						continue;
					matchedServerRefs = Collections.singleton(ref);
				}
				for (Ref ref : matchedServerRefs) {
					ObjectId start = clientState.get(ref.getName());
					if (start == null)
						start = ObjectId.zeroId();
					updateCommands.add(new ReceiveCommand(start
							.getObjectId()
				}
			}
			it.prepend(new PublisherPush(name
					+ session.getKey() + "-" + idCounter++
					.getPackFactory()));
		}
	}

	private void readHeaders() throws IOException {
		String line;
		while ((line = in.readString()) != PacketLineIn.END) {
			if (line.startsWith("restart "))
				restartToken = line.substring("restart ".length());
			else if (line.startsWith("last-pack-number "))
				lastPackId = line.substring("last-pack-number ".length());
		}
	}

	private void readSubscribeCommands()
			throws IOException
		String line;
		ArrayList<SubscribeCommand> cmdList = null;
		Map<String
		String repo = null;
		while (!(line = in.readString()).startsWith("done")) {
			if (line == PacketLineIn.END) {
				commands.put(repo
				refState.put(repo
				repo = null;
				cmdList = null;
				stateList = null;
			} else if (line.startsWith("repository ")) {
				repo = line.substring("repository ".length());
				cmdList = new ArrayList<SubscribeCommand>();
				stateList = new HashMap<String
			} else {
				if (repo == null || cmdList == null || stateList == null)
					throw new IOException(MessageFormat.format(JGitText
							.get().invalidSubscribeRequest
				if (line.startsWith("want "))
					cmdList.add(new SubscribeCommand(SUBSCRIBE
							"want ".length())));
				else if (line.startsWith("stop "))
					throw new TransportException("stop command not supported");
				else if (line.startsWith("have ")) {
					String[] parts = line.split(" "
					stateList.put(parts[2]
				}
			}
		}
	}

	public PublisherSession getSession() {
		return session;
	}

	public synchronized void close() {
		if (!closed) {
			closed = true;
			try {
				out.close();
			} catch (IOException e) {
			} finally {
				out = null;
			}
			if (consumeThread != null) {
				consumeThread.interrupt();
			}
		}
	}

	public boolean isClosed() {
		return closed;
	}

	Map<String
		return commands;
	}

	Map<String
		return refState;
	}

	public abstract Repository openRepository(String name)
			throws RepositoryNotFoundException
			ServiceNotAuthorizedException
}
