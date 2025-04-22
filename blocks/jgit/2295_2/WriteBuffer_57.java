
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.ChunkInfo;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectListInfo;
import org.eclipse.jgit.storage.dht.RepositoryKey;

public interface RepositoryTable {
	public void put(RepositoryKey repo
			throws DhtException;

	public void removeInfo(RepositoryKey repo
			WriteBuffer buffer) throws DhtException;

	public Collection<ObjectListInfo> getObjectLists(RepositoryKey repo)
			throws DhtException

	public void put(RepositoryKey repo
			throws DhtException;
}
