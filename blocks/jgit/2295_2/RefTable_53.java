
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectListChunk;
import org.eclipse.jgit.storage.dht.ObjectListChunkKey;

public interface ObjectListTable {
	public void get(Context options
			AsyncCallback<Collection<ObjectListChunk>> callback);

	public void put(ObjectListChunk listChunk
			throws DhtException;
}
