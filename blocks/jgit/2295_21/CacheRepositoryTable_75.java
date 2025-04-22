
package org.eclipse.jgit.storage.dht.spi.cache;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;

import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;

public class CacheRepositoryIndexTable implements RepositoryIndexTable {
	private final RepositoryIndexTable db;

	private final CacheService client;

	private final CacheOptions options;

	private final Namespace ns;

	private final Sync<Void> none;

	public CacheRepositoryIndexTable(RepositoryIndexTable dbTable
			CacheDatabase cacheDatabase) {
		this.db = dbTable;
		this.client = cacheDatabase.getClient();
		this.options = cacheDatabase.getOptions();
		this.ns = Namespace.REPOSITORY_INDEX;
		this.none = Sync.none();
	}

	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException {
		CacheKey memKey = ns.key(name);
		Sync<Map<CacheKey
		client.get(singleton(memKey)

		Map<CacheKey
		try {
			result = sync.get(options.getTimeout());
		} catch (InterruptedException e) {
			throw new TimeoutException();
		} catch (TimeoutException timeout) {
			result = emptyMap();
		}

		byte[] data = result.get(memKey);
		if (data != null) {
			if (data.length == 0)
				return null;
			return RepositoryKey.fromBytes(data);
		}

		RepositoryKey key = db.get(name);
		data = key != null ? key.asBytes() : new byte[0];
		client.modify(singleton(Change.put(memKey
		return key;
	}

	public void putUnique(RepositoryName name
			throws DhtException
		db.putUnique(name

		Sync<Void> sync = Sync.create();
		CacheKey memKey = ns.key(name);
		byte[] data = key.asBytes();
		client.modify(singleton(Change.put(memKey
		try {
			sync.get(options.getTimeout());
		} catch (InterruptedException e) {
			throw new TimeoutException();
		}
	}
}
