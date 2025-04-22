
package org.eclipse.jgit.storage.dht.spi;

import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;

public interface RepositoryIndexTable {
	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException;

	public void putUnique(RepositoryName name
			throws DhtException
}
