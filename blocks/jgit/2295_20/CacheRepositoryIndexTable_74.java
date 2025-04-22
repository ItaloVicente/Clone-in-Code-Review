
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.RefTable;

public class CacheRefTable implements RefTable {
	private final RefTable db;

	public CacheRefTable(RefTable dbTable
		this.db = dbTable;
	}

	public Map<RefKey
			throws DhtException
		return db.getAll(options
	}

	public boolean compareAndRemove(RefKey refKey
			throws DhtException
		return db.compareAndRemove(refKey
	}

	public boolean compareAndPut(RefKey refKey
			throws DhtException
		return db.compareAndPut(refKey
	}
}
