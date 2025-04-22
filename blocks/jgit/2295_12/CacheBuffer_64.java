
package org.eclipse.jgit.storage.dht.spi;

import org.eclipse.jgit.storage.dht.DhtException;

public interface WriteBuffer {
	public void flush() throws DhtException;

	public void abort() throws DhtException;
}
