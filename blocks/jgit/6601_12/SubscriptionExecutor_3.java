
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.transport.PubSubConfig.Publisher;
import org.eclipse.jgit.transport.SubscribeCommand.Command;

class SubscribeProcess {
	private static class Backoff {
		private int n;

		private final int max;


		private long lastCall;

		private Backoff(int max
			this.max = max;
			this.timeout = ms;
		}

		private void backoff() throws InterruptedException {
			if (milliTime() - lastCall > timeout)
				reset();
			n += 1;
			double r = Math.random() + 0.5;
			double exp = Math.pow(2
			long delay = (long) (r * timeout * exp);
			Thread.sleep(Math.min(max
			lastCall = milliTime();
		}

		private void reset() {
			n = 0;
		}

		private long milliTime() {
			return System.nanoTime() / 1000000;
		}
	}


	private final Transport transport;

	private SubscribeConnection connection;

	private final SubscribeState subscriber;

	private int timeout;

	public SubscribeProcess(URIish uri
			throws NotSupportedException
		subscriber = s;
		timeout = SUBSCRIBE_TIMEOUT;
		transport = Transport.open(uri);
	}

	Map<String
			PubSubConfig.Publisher publisher)
			throws IOException
		Map<String
				String

		for (PubSubConfig.Subscriber s : publisher.getSubscribers()) {
			SubscribedRepository sr = subscriber.getRepository(s.getName());
			List<SubscribeCommand> repoCommands = new ArrayList<
					SubscribeCommand>();

			if (sr == null) {
				sr = new SubscribedRepository(s);
				for (RefSpec spec : s.getSubscribeSpecs())
					repoCommands.add(new SubscribeCommand(
							Command.SUBSCRIBE
			} else {
				List<String> newSpecs = new ArrayList<String>();
				List<String> oldSpecs = new ArrayList<String>();
				for (RefSpec rs : s.getSubscribeSpecs())
					newSpecs.add(rs.getSource());
				for (RefSpec rs : sr.getSubscribeSpecs())
					oldSpecs.add(rs.getSource());

				Set<String> toAdd = new LinkedHashSet<String>(newSpecs);
				toAdd.removeAll(oldSpecs);
				for (String subscribe : toAdd)
					repoCommands.add(
							new SubscribeCommand(Command.SUBSCRIBE

				Set<String> toRemove = new LinkedHashSet<String>(oldSpecs);
				toRemove.removeAll(newSpecs);
				for (String unsubscribe : toRemove)
					repoCommands.add(new SubscribeCommand(
							Command.UNSUBSCRIBE

				sr.setSubscribeSpecs(s.getSubscribeSpecs());
			}

			subscribeCommands.put(sr.getName()
			subscriber.putRepository(sr.getName()
			sr.setUpRefs();
		}
		return subscribeCommands;
	}

	public void execute(Publisher publisher
			throws NotSupportedException
			URISyntaxException {
		Map<String
		Backoff backoff = new Backoff(240 * 1000
		while (true) {
			if (Thread.interrupted())
				throw new InterruptedException();
			try {
				doSubscribe(commands
			} catch (NotSupportedException e) {
				throw e;
			} catch (TransportException e) {
				throw e;
			} catch (IOException e) {
				if (subscriber.getRestartToken() != null)
					commands = Collections.emptyMap();
				backoff.backoff();
				continue;
			}
			backoff.reset();
			subscriber.reset();
			commands = applyConfig(publisher);
		}
	}

	void doSubscribe(
			Map<String
			throws NotSupportedException
			InterruptedException
		transport.setTimeout(timeout);
		connection = transport.openSubscribe(subscriber);
		try {
			connection.subscribe(subscriber
		} finally {
			connection.close();
			connection = null;
		}
	}

	SubscribeState getSubscriber() {
		return subscriber;
	}

	void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void close() {
		transport.close();
		subscriber.close();
	}
}
