
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;
import org.eclipse.jgit.storage.dht.ObjectInfo;

public interface ObjectIndexTable {
	public void get(Context options
			AsyncCallback<Map<ObjectIndexKey

	public void add(ObjectIndexKey objId
			throws DhtException;

	public void replace(ObjectIndexKey objId
			WriteBuffer buffer) throws DhtException;

	public void remove(ObjectIndexKey objId
			throws DhtException;
}
