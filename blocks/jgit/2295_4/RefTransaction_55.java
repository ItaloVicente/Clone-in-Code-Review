
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

	public RefTransaction newTransaction(RefKey refKey) throws DhtException
			TimeoutException;
}
