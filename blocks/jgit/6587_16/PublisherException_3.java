
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
	private static class Header {
		private String lastPackId;

		private String restartToken;
	}

	private static class Request {
		private Map<String
				commands = new HashMap<String

		private Map<String
				clientRefState = new HashMap<String
	}


	private final Publisher publisher;

	private PublisherSession session;

	private PacketLineIn in;

	private OutputStream out;

	private int heartbeatInterval;

	public PublisherClient(Publisher p) {
		publisher = p;
		heartbeatInterval = HEARTBEAT_INTERVAL;
	}

	public void setHeartbeatInterval(int ms) {
		heartbeatInterval = ms;
	}

	public void subscribeLoop(
			InputStream myIn
			throws IOException
			ServiceNotEnabledException {
		in = new PacketLineIn(myIn);
		out = new BufferedOutputStream(myOut);

		try {
			String line = in.readString();
			if (line.equals("advertisement"))
				doAdvertisement();
			else if (line.equals("subscribe"))
				doSubscribe();
			else
				throw new TransportException(MessageFormat.format(
						JGitText.get().expectedGot
						line));
		} finally {
			in = null;
			out.close();
			out = null;
		}
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
				Repository r = openRepository(
						line.substring("repositoryaccess ".length()));
				r.close();
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
		Header header = readHeaders();
		Request request = readSubscribeCommands();

		PacketLineOut pktLineOut = new PacketLineOut(out);
		if (request.commands.size() > 0 && header.restartToken != null) {
			pktLineOut.writeString("reconnect");
			pktLineOut.end();
			return;
		}

		Window stream;

		if (header.restartToken != null) {
			session = publisher.reconnectSession(header.restartToken);
			if (session == null) {
				pktLineOut.writeString("reconnect");
				pktLineOut.end();
				return;
			}
			stream = session.getStream();
			if (header.lastPackId != null) {
				if (!stream.rollback(header.lastPackId)) {
					pktLineOut.writeString("reconnect");
					pktLineOut.end();
					return;
				}
			}
		} else {
			session = publisher.newSession();
			stream = session.getStream();
			session.sync(request.commands);
			generateInitialPacks(request.clientRefState);
		}

		header = null;
		request = null;

		pktLineOut.writeString("ACK");
		pktLineOut.writeString("restart-token " + session.getKey());
		pktLineOut.writeString(
				"heartbeat-interval " + heartbeatInterval / 1000);
		pktLineOut.flush();

		Thread consumeThread = Thread.currentThread();
		String oldThreadName = consumeThread.getName();
		consumeThread.setName("PubSub Consumer " + session.getKey());
		try {
			while (true) {
				if (Thread.interrupted())
					throw new InterruptedException();
				sendUpdate(pktLineOut
			}
		} catch (InterruptedException e) {
		} finally {
			consumeThread.setName(oldThreadName);
			consumeThread = null;
			publisher.startDeleteTimer(session);
		}
	}

	private void sendUpdate(PacketLineOut pktLineOut
			throws InterruptedException
			ServiceNotAuthorizedException
		int waitTime = heartbeatInterval;
		PublisherPush push = null;
		PublisherPack pack = null;
		while (waitTime > 0) {
			long startTime = System.currentTimeMillis();
			push = stream.next(waitTime
			waitTime -= (System.currentTimeMillis() - startTime);
			if (push == null)
				break;
			pack = push.get(this);
			if (pack == null)
			stream.mark();
			break;
		}
		if (publisher.isClosed())
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

	private void generateInitialPacks(
			Map<String
			throws RepositoryNotFoundException
			ServiceMayNotContinueException
			ServiceNotAuthorizedException
			ServiceNotEnabledException
			IOException {
		Window it = session.getStream();
		int idCounter = 0;
		for (String name : session.getSubscribedRepositoryNames()) {
			List<ReceiveCommand> updateCommands = new ArrayList<
					ReceiveCommand>();
			Map<String
			if (clientState == null)
				clientState = Collections.emptyMap();
			Repository r = openRepository(name);
			try {
				publisher.hookRepository(r
				RefDatabase refDb = r.getRefDatabase();
				for (SubscribeSpec spec : session.getSubscriptions(name)) {
					Collection<Ref> matchedServerRefs;
					if (spec.isWildcard()) {
						String ref = spec.getRefName();
						matchedServerRefs = refDb.getRefs(
								SubscribeSpec.stripWildcard(ref)).values();
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
						ObjectId end = ref.getObjectId();
						if (start.equals(end))
							continue;
						updateCommands.add(
								new ReceiveCommand(start
					}
				}
				if (updateCommands.size() == 0)
					continue;
				it.prepend(new PublisherPush(name
						+ session.getKey() + "-" + idCounter++
						.getPackFactory()));
			} finally {
				r.close();
			}
		}
	}

	private Header readHeaders() throws IOException {
		Header h = new Header();
		String line;
		while ((line = in.readString()) != PacketLineIn.END) {
			if (line.startsWith("restart "))
				h.restartToken = line.substring("restart ".length());
			else if (line.startsWith("last-pack-id "))
				h.lastPackId = line.substring("last-pack-id ".length());
		}
		return h;
	}

	private Request readSubscribeCommands()
			throws IOException
		Request r = new Request();
		String line;
		ArrayList<SubscribeCommand> cmdList = null;
		Map<String
		String repo = null;
		while (!(line = in.readString()).startsWith("done")) {
			if (line == PacketLineIn.END && repo != null) {
				r.commands.put(repo
				r.clientRefState.put(repo
				repo = null;
				cmdList = null;
				stateList = null;
			} else if (line.startsWith("repository ")) {
				repo = line.substring("repository ".length());
				cmdList = new ArrayList<SubscribeCommand>();
				stateList = new HashMap<String
			} else {
				if (repo == null || cmdList == null || stateList == null)
					throw new TransportException(MessageFormat.format(JGitText
							.get().invalidSubscribeRequest
				if (line.startsWith("want "))
					cmdList.add(new SubscribeCommand(SUBSCRIBE
							"want ".length())));
				else if (line.startsWith("stop "))
					throw new TransportException(
							JGitText.get().stopCommandNotSupported);
				else if (line.startsWith("have ")) {
					String[] parts = line.split(" "
					if (parts.length != 3)
						throw new TransportException(MessageFormat.format(
								JGitText.get().invalidSubscribeRequest
					stateList.put(parts[2]
				}
			}
		}
		return r;
	}

	public PublisherSession getSession() {
		return session;
	}

	public abstract Repository openRepository(String name)
			throws RepositoryNotFoundException
			ServiceNotAuthorizedException
}
