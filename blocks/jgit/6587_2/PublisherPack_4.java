
package org.eclipse.jgit.transport;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.SubscribeCommand.Command;

public class PublisherClient {
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

	public void subscribe(final InputStream myIn
			final OutputStream messages) throws IOException {
		this.in = new PacketLineIn(myIn);
		this.out = new BufferedOutputStream(myOut);

		readFastRestart();
		readSubscribeSpec();
		readRefState();

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
		pktLineOut.writeString("fast-restart " + clientState.getKey());
		pktLineOut.end();

		consumeThread = Thread.currentThread();
		String oldThreadName = consumeThread.getName();
		consumeThread.setName("PubSub Consumer " + clientState.getKey());
		try {
			while (true) {
				PublisherPack pk = clientState.getNextUpdate(
						HEARTBEAT_INTERVAL);
				if (pk == null) {
					pktLineOut.writeString("heartbeat");
					pktLineOut.end();
				} else {
					pktLineOut.writeString("update " + pk.getRepositoryName());
					PublisherPackSlice slice;
					for (Iterator<PublisherPackSlice> it = pk.getSlices();
							it.hasNext();) {
						slice = it.next();
						slice.writeToStream(out);
					}
					pktLineOut.writeString("sequence " + pk.getPackNumber());
					pktLineOut.end();
					pk.release();
				}
			}
		} catch (IOException e) {
			System.err.println("Client disconnected");
			clientState.rollbackUpdateStream();
			throw e;
		} catch (InterruptedException e) {
			System.err.println("Interrupted while polling");
			e.printStackTrace();
		} finally {
			consumeThread.setName(oldThreadName);
			consumeThread = null;
			clientState.disconnect();
		}
	}

	private void readFastRestart() throws IOException {
		String parts[] = in.readString().split(" "
		if (!parts[0].equals("fast-restart")) {
			return;
		}
		if (parts.length < 2)
			throw new IOException("Invalid fast-restart line");
		restartToken = parts[1];
		if (parts.length > 2)
			lastPackNumber = Integer.parseInt(parts[2]);
	}

	private void readSubscribeSpec() throws IOException {
		String line;
		ArrayList<SubscribeCommand> cmdList = null;
		String repo = null;
		while ((line = in.readString()) != PacketLineIn.END) {
			String parts[] = line.split(" "
			String type = parts[0];
			if (type.equals("repo")) {
				if (repo != null)
					commands.put(repo
				cmdList = new ArrayList<SubscribeCommand>();
				repo = parts[1];
			} else {
				if (cmdList == null)
					throw new IOException("Invalid subscribe request");
				if (type.equals("subscribe")) {
					cmdList.add(
							new SubscribeCommand(Command.SUBSCRIBE
				} else if (type.equals("unsubscribe")) {
					cmdList.add(new SubscribeCommand(
							Command.UNSUBSCRIBE
				}
			}
		}
		if (repo != null)
			commands.put(repo
	}

	private void readRefState() throws IOException {
		String line;
		Map<String
		String repo = null;
		while ((line = in.readString()) != PacketLineIn.END) {
			String parts[] = line.split(" "
			String type = parts[0];
			if (type.equals("repo")) {
				if (repo != null)
					refState.put(repo
				stateList = new HashMap<String
				repo = parts[1];
			} else {
				if (stateList == null)
					throw new IOException("Invalid subscribe request");
				stateList.put(parts[1]
			}
		}
		if (repo != null)
			refState.put(repo
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
}
