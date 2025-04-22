
package org.eclipse.jgit.storage.dht.spi;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;

import java.util.concurrent.TimeoutException;

public interface RepositoryTable {
	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException;

	public void putUnique(RepositoryName name
			throws DhtException
}
