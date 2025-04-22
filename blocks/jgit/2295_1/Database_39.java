
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;

public interface ChunkTable {
	public void get(Set<ChunkKey> keys
			AsyncCallback<Collection<PackChunk>> callback);

	public void putData(ChunkKey key
			throws DhtException;

	public void putIndex(ChunkKey key
			throws DhtException;

	public void putFragments(ChunkKey key
			throws DhtException;
}
