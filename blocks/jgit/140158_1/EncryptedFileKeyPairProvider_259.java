package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;

import org.eclipse.jgit.transport.sshd.KeyCache;

public class CachingKeyPairProvider extends EncryptedFileKeyPairProvider
		implements Iterable<KeyPair> {

	private final KeyCache cache;

	public CachingKeyPairProvider(List<Path> paths
		super(paths);
		this.cache = cache;
	}

	@Override
	public Iterator<KeyPair> iterator() {
		Collection<? extends Path> resources = getPaths();
		if (resources.isEmpty()) {
			return Collections.emptyListIterator();
		}
		return new CancellingKeyPairIterator(resources);
	}

	@Override
	public Iterable<KeyPair> loadKeys() {
		return this;
	}

	@Override
	protected KeyPair doLoadKey(Path resource)
			throws IOException
		if (!Files.exists(resource)) {
			log.warn(format(SshdText.get().identityFileNotFound
			return null;
		}
		String resourceId = resource.toString();
		if (cache == null) {
			return doLoadKey(resourceId
		}
		Throwable t[] = { null };
		KeyPair key = cache.get(resource
			try {
				return doLoadKey(resourceId
			} catch (IOException | GeneralSecurityException e) {
				t[0] = e;
				return null;
			}
		});
		if (t[0] != null) {
			if (t[0] instanceof CancellationException) {
				throw (CancellationException) t[0];
			}
			throw new IOException(
					format(SshdText.get().keyLoadFailed
		}
		return key;
	}

	private class CancellingKeyPairIterator implements Iterator<KeyPair> {

		private final Iterator<Path> paths;

		private KeyPair nextItem;

		private boolean nextSet;

		public CancellingKeyPairIterator(Collection<? extends Path> resources) {
			List<Path> copy = new ArrayList<>(resources.size());
			copy.addAll(resources);
			paths = copy.iterator();
		}

		@Override
		public boolean hasNext() {
			if (nextSet) {
				return nextItem != null;
			}
			nextSet = true;
			while (nextItem == null && paths.hasNext()) {
				try {
					nextItem = doLoadKey(paths.next());
				} catch (CancellationException cancelled) {
					throw cancelled;
				} catch (Exception other) {
					log.warn(other.toString());
				}
			}
			return nextItem != null;
		}

		@Override
		public KeyPair next() {
			if (!nextSet && !hasNext()) {
				throw new NoSuchElementException();
			}
			KeyPair result = nextItem;
			nextItem = null;
			nextSet = false;
			if (result == null) {
				throw new NoSuchElementException();
			}
			return result;
		}

	}
}
