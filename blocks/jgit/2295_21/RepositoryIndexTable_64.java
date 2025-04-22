
package org.eclipse.jgit.storage.dht.spi;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;

public interface RefTable {
	public Map<RefKey
			throws DhtException

	public boolean compareAndRemove(RefKey refKey
			throws DhtException

	public boolean compareAndPut(RefKey refKey
			throws DhtException
}
