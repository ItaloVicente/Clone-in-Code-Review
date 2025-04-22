
package org.eclipse.jgit.storage.dht.spi.cache;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.ChunkInfo;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectListInfo;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.TinyProtobuf;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;

final class CacheRepositoryTable implements RepositoryTable {
	private final RepositoryTable db;

	private final CacheService client;

	private final CacheOptions options;

	private final Namespace nsObjectList = Namespace.OBJECT_LIST;

	private final Sync<Void> none;

	CacheRepositoryTable(RepositoryTable db
		this.db = db;
		this.client = mem.getClient();
		this.options = mem.getOptions();
		this.none = Sync.none();
	}

	public void put(RepositoryKey repo
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.put(repo
	}

	public void removeInfo(RepositoryKey repo
			WriteBuffer buffer) throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.removeInfo(repo
	}

	public Collection<ObjectListInfo> getObjectLists(RepositoryKey repo)
			throws DhtException
		CacheKey memKey = nsObjectList.key(repo);
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
			List<ObjectListInfo> r = new ArrayList<ObjectListInfo>();
			TinyProtobuf.Decoder d = TinyProtobuf.decode(data);
			for (;;) {
				switch (d.next()) {
				case 0:
					return r;
				case 1:
					r.add(ObjectListInfo.fromBytes(repo
					continue;
				default:
					d.skip();
				}
			}
		}

		Collection<ObjectListInfo> r = db.getObjectLists(repo);
		TinyProtobuf.Encoder e = TinyProtobuf.encode(128);
		for (ObjectListInfo info : r)
			e.bytes(1
		client.modify(singleton(Change.put(memKey
		return r;
	}

	public void put(RepositoryKey repo
			throws DhtException {
		CacheBuffer buf = (CacheBuffer) buffer;
		db.put(repo
		buf.removeAfterFlush(nsObjectList.key(repo));
	}
}
