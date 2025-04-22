
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.CachedPackInfo;
import org.eclipse.jgit.storage.dht.CachedPackKey;
import org.eclipse.jgit.storage.dht.ChunkInfo;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;

public interface RepositoryTable {
	public RepositoryKey nextKey() throws DhtException;

	public void put(RepositoryKey repo
			throws DhtException;

	public void remove(RepositoryKey repo
			throws DhtException;

	public Collection<CachedPackInfo> getCachedPacks(RepositoryKey repo)
			throws DhtException

	public void put(RepositoryKey repo
			throws DhtException;

	public void remove(RepositoryKey repo
			throws DhtException;
}
