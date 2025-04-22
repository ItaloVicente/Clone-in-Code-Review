
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PublisherStream.RefCounted;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class PublisherPush implements RefCounted {
	public static PublisherPush equalityInstance(String pushId) {
		return new PublisherPush(pushId);
	}

	private final PublisherPackFactory factory;

	private final String repositoryName;

	private final String pushId;

	private final AtomicInteger refCount;

	private final List<PublisherPack> packs;

	private final Collection<ReceiveCommand> commands;

	private volatile boolean closed;

	public PublisherPush(String name
			String id
		repositoryName = name;
		pushId = id;
		refCount = new AtomicInteger();
		packs = new ArrayList<PublisherPack>();
		this.commands = commands;
		this.factory = factory;
	}

	private PublisherPush(String id) {
		repositoryName = null;
		pushId = id;
		refCount = null;
		packs = null;
		commands = null;
		factory = null;
	}

	public PublisherPack get(PublisherClient client) throws IOException
			ServiceNotAuthorizedException
		if (closed)
			throw new IOException("Push already closed");
		PublisherSession session = client.getSession();
		Collection<SubscribeSpec> specs = session.getSubscriptions(
				repositoryName);
		if (specs.size() == 0)
			return null;
		Repository r = client.openRepository(repositoryName);
		try {
			synchronized (this) {
				for (PublisherPack p : packs) {
					if (p.match(specs))
						return p;
				}
				PublisherPack newPack = generate(
						r
				packs.add(newPack);
				return newPack;
			}
		} finally {
			r.close();
		}
	}

	public void setReferences(int number) {
		refCount.set(number);
	}

	public void decrement() {
		if (refCount.decrementAndGet() == 0)
			close();
	}

	private synchronized void close() {
		if (closed)
			return;
		for (PublisherPack p : packs)
			p.close();
		closed = true;
	}

	@Override
	public int hashCode() {
		return pushId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PublisherPush))
			return false;
		PublisherPush o = (PublisherPush) obj;
		return o.pushId == pushId;
	}

	private PublisherPack generate(Repository db
			Collection<ReceiveCommand> updates
			String packId) throws IOException {
		Set<ReceiveCommand> matchedUpdates = new HashSet<
				ReceiveCommand>();
		for (ReceiveCommand r : updates) {
			for (SubscribeSpec f : specs) {
				if (f.isMatch(r.getRefName())) {
					matchedUpdates.add(r);
					break;
				}
			}
		}
		return factory.buildPack(db
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getPushId() {
		return pushId;
	}

	@Override
	public String toString() {
		return "PublisherPush[num=" + pushId + "
	}
}
