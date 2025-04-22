
package org.eclipse.jgit.transport;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.Publisher.PublisherException;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class PublisherPush implements Closeable {
	private class Generator {
		private PublisherPack pack;

		private final Repository db;

		private final Collection<ReceiveCommand> updates;

		private final Set<SubscribeSpec> specs;

		private final String packId;

		private Generator(Repository db
				Collection<SubscribeSpec> specs
			this.db = db;
			this.updates = updates;
			this.specs = new HashSet<SubscribeSpec>(specs);
			this.packId = packId;
			db.incrementOpen();
		}

		private synchronized PublisherPack get() throws IOException {
			if (pack != null)
				return pack;
			pack = factory.buildPack(db
			db.close();
			return pack;
		}

		private boolean match(Set<SubscribeSpec> subscribeSpecs) {
			return specs.equals(subscribeSpecs);
		}

		private void close() throws PublisherException {
			pack.close();
		}
	}

	private final PublisherPackFactory factory;

	private final String repositoryName;

	private final String pushId;

	private final List<Generator> packs;

	private final Collection<ReceiveCommand> commands;

	private volatile boolean closed;

	public PublisherPush(String name
			String id
		repositoryName = name;
		pushId = id;
		packs = new ArrayList<Generator>();
		this.commands = commands;
		this.factory = factory;
	}

	public PublisherPack get(PublisherClient client) throws IOException
			ServiceNotAuthorizedException
		if (closed)
			throw new IOException("Push already closed");
		PublisherSession session = client.getSession();
		Set<SubscribeSpec> specs = session.getSubscriptions(repositoryName);
		if (specs.size() == 0)
			return null;
		Repository r = client.openRepository(repositoryName);
		try {
			Generator packGenerator = null;
			synchronized (this) {
				for (Generator p : packs) {
					if (p.match(specs))
						packGenerator = p;
				}
				if (packGenerator == null) {
					Set<ReceiveCommand> matchedUpdates = new HashSet<
							ReceiveCommand>();
					for (ReceiveCommand rc : commands) {
						for (SubscribeSpec f : specs) {
							if (f.isMatch(rc.getRefName())) {
								matchedUpdates.add(rc);
								break;
							}
						}
					}
					if (matchedUpdates.size() == 0)
						return null;
					packGenerator = new Generator(r
							matchedUpdates
					packs.add(packGenerator);
				}
			}
			return packGenerator.get();
		} finally {
			r.close();
		}
	}

	public synchronized void close() throws PublisherException {
		if (closed)
			return;
		for (Generator p : packs)
			p.close();
		closed = true;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getPushId() {
		return pushId;
	}

	@Override
	public String toString() {
		return "PublisherPush[id=" + pushId + "
	}
}
