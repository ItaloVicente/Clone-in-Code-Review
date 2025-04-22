
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.storage.dht.spi.RefTransaction;

final class CacheRefTable implements RefTable {
	private final RefTable db;

	CacheRefTable(RefTable db
		this.db = db;
	}

	public Map<RefKey
			throws DhtException
		return db.getAll(options
	}

	public RefTransaction newTransaction(RefKey refKey) throws DhtException
			TimeoutException {
		return db.newTransaction(refKey);
	}
}
