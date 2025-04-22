
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.SubscribeCommand.Command.SUBSCRIBE;
import static org.eclipse.jgit.transport.SubscribeCommand.Command.UNSUBSCRIBE;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public abstract class PublisherClient {
	private static final int HEARTBEAT_INTERVAL = 10000;

	private final Publisher publisher;

	private Map<String

	private Map<String

	private PacketLineIn in;

	private OutputStream out;

	private String restartToken;

	private int lastPackNumber;

	private volatile boolean closed;

	private Thread consumeThread;

	public PublisherClient(Publisher p) {
		lastPackNumber = -1;
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

		readRestart();
		boolean isAdvertisment = readSubscribeCommands();

		PublisherSession clientState = publisher.connectClient(this);
		PacketLineOut pktLineOut = new PacketLineOut(out);
		if (clientState == null) {
			pktLineOut.writeString("reconnect");
			pktLineOut.end();
			return;
		}
		if (lastPackNumber != -1) {
			PublisherPack nextPack = clientState.peekNextUpdate();
			if (nextPack == null
					|| nextPack.getPackNumber() != lastPackNumber) {
				pktLineOut.writeString("reconnect");
				pktLineOut.end();
				return;
			}
		}
		pktLineOut.writeString("restart-token " + clientState.getKey());
		pktLineOut.writeString(
				"heartbeat-interval " + HEARTBEAT_INTERVAL / 1000);
		pktLineOut.end();

		if (isAdvertisment) {
			clientState.disconnect();
			return;
		}

		consumeThread = Thread.currentThread();
		String oldThreadName = consumeThread.getName();
		consumeThread.setName("PubSub Consumer " + clientState.getKey());
		try {
			while (true) {
				if (Thread.interrupted())
					throw new InterruptedException();
				PublisherPack pk = clientState.getNextUpdate(
						HEARTBEAT_INTERVAL);
				if (closed)
					throw new EOFException();
				if (pk == null)
					pktLineOut.writeString("heartbeat");
				else {
					pktLineOut.writeString("update " + pk.getRepositoryName());
					for (Iterator<PublisherPackSlice> it = pk.getSlices();
							it.hasNext();)
						it.next().writeToStream(out);
					pktLineOut.writeString("sequence " + pk.getPackNumber());
					pk.release();
				}
				pktLineOut.flush();
			}
		} catch (IOException e) {
			clientState.rollbackUpdateStream();
		} catch (InterruptedException e) {
		} finally {
			consumeThread.setName(oldThreadName);
			consumeThread = null;
			clientState.disconnect();
		}
	}

	private void readRestart() throws IOException {
		String line;
		while ((line = in.readString()) != PacketLineIn.END) {
			if (line.startsWith("restart "))
				restartToken = line.substring("restart ".length());
			else if (line.startsWith("last-pack "))
				lastPackNumber = Integer.parseInt(line.substring(
						"last-pack ".length()));
		}
	}

	private boolean readSubscribeCommands() throws IOException {
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
					cmdList.add(new SubscribeCommand(UNSUBSCRIBE
							.substring("stop ".length())));
				else if (line.startsWith("have ")) {
					String[] parts = line.split(" "
					stateList.put(parts[2]
				}
			}
		}
		return line.endsWith(" advertisement");
	}

	String getRestartToken() {
		return restartToken;
	}

	Map<String
		return commands;
	}

	Map<String
		return refState;
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

	public OutputStream getOutputStream() {
		return out;
	}

	public abstract Repository openRepository(String name)
			throws RepositoryNotFoundException
			ServiceNotAuthorizedException
}
