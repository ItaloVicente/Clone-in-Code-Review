
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.SubscribeCommand.Command;

public class Subscriber {
	public final static int SUBSCRIBE_TIMEOUT = 3 * 60 * 60;

	private final Transport transport;

	private PubSubConfig.Publisher config;

	private SubscribeConnection connection;

	private String restartToken;

	private String restartSequence;

	private final Map<String

	private int timeout;

	public Subscriber(URIish uri) throws IOException {
		transport = Transport.open(uri);
		repoSubscriptions = new HashMap<String
		timeout = SUBSCRIBE_TIMEOUT;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Map<String
			PubSubConfig.Publisher publisher) throws IOException {
		config = publisher;
		Map<String
				String

		for (PubSubConfig.Subscriber s : publisher.getSubscribers()) {
			SubscribedRepository sr = repoSubscriptions.get(s.getName());
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

				List<String> toAdd = new ArrayList<String>(newSpecs);
				toAdd.removeAll(oldSpecs);
				for (String subscribe : toAdd)
					repoCommands.add(
							new SubscribeCommand(Command.SUBSCRIBE

				List<String> toRemove = new ArrayList<String>(oldSpecs);
				toRemove.removeAll(newSpecs);
				for (String unsubscribe : toRemove)
					repoCommands.add(new SubscribeCommand(
							Command.UNSUBSCRIBE

				sr.setSubscribeSpecs(s.getSubscribeSpecs());
			}

			subscribeCommands.put(sr.getName()
			repoSubscriptions.put(sr.getName()
			sr.setUpRefs();
		}
		return subscribeCommands;
	}

	public void subscribe(final Map<String
			ProgressMonitor monitor) throws NotSupportedException
			InterruptedException
		connection = transport.openSubscribe();
		transport.setTimeout(timeout);
		try {
			connection.doSubscribe(this
		} finally {
			close();
		}
	}

	public String getKey() {
		return config.getKey();
	}

	public SubscribedRepository getRepository(String r) {
		return repoSubscriptions.get(r);
	}

	public String getRestartToken() {
		return restartToken;
	}

	public void setRestartToken(String restart) {
		restartToken = restart;
	}

	public String getRestartSequence() {
		return restartSequence;
	}

	public void setRestartSequence(String sequence) {
		restartSequence = sequence;
	}

	public void close() {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
}
