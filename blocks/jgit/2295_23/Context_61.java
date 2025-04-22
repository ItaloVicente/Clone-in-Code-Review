
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.ChunkMeta;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;
import org.eclipse.jgit.storage.dht.StreamingCallback;

public interface ChunkTable {
	public void get(Context options
			AsyncCallback<Collection<PackChunk.Members>> callback);

	public void getMeta(Context options
			AsyncCallback<Collection<ChunkMeta>> callback);

	public void put(PackChunk.Members chunk
			throws DhtException;

	public void remove(ChunkKey key
}
