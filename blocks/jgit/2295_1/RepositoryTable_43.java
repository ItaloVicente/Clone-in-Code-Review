
package org.eclipse.jgit.storage.dht.spi;

import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;

public interface RefTransaction {
	public RefData getOldData();

	public boolean compareAndPut(RefData newData) throws DhtException
			TimeoutException;

	public void abort();
}
