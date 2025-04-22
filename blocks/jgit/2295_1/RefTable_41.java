
package org.eclipse.jgit.storage.dht.spi;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkLink;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;

public interface ObjectIndexTable {
	public void get(Set<ObjectIndexKey> objects
			AsyncCallback<Map<ObjectIndexKey

	public void add(ObjectIndexKey objId
			throws DhtException;

	public void replace(ObjectIndexKey objId
			WriteBuffer buffer) throws DhtException;
}
