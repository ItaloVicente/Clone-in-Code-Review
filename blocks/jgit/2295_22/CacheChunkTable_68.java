
package org.eclipse.jgit.storage.dht.spi.cache;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.Sync;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.cache.CacheService.Change;
import org.eclipse.jgit.storage.dht.spi.util.AbstractWriteBuffer;

public class CacheBuffer extends AbstractWriteBuffer {
	private final WriteBuffer dbBuffer;

	private final CacheService client;

	private final Sync<Void> none;

	private List<CacheService.Change> pending;

	private List<CacheService.Change> afterFlush;

	public CacheBuffer(WriteBuffer dbBuffer
			CacheOptions options) {
		super(null
		this.dbBuffer = dbBuffer;
		this.client = client;
		this.none = Sync.none();
	}

	public void remove(CacheKey key) throws DhtException {
		modify(CacheService.Change.remove(key));
	}

	public void removeAfterFlush(CacheKey key) {
		if (afterFlush == null)
			afterFlush = newList();
		afterFlush.add(CacheService.Change.remove(key));
	}

	public void put(CacheKey key
		modify(CacheService.Change.put(key
	}

	public void modify(CacheService.Change op) throws DhtException {
		int sz = op.getKey().getBytes().length;
		if (op.getData() != null)
			sz += op.getData().length;
		if (add(sz)) {
			if (pending == null)
				pending = newList();
			pending.add(op);
			queued(sz);
		} else {
			client.modify(singleton(op)
		}
	}

	public WriteBuffer getWriteBuffer() {
		return dbBuffer;
	}

	@Override
	protected void startQueuedOperations(int bytes) throws DhtException {
		client.modify(pending
		pending = null;
	}

	public void flush() throws DhtException {
		dbBuffer.flush();

		if (afterFlush != null) {
			for (CacheService.Change op : afterFlush)
				modify(op);
			afterFlush = null;
		}

		super.flush();
	}

	@Override
	public void abort() throws DhtException {
		pending = null;
		afterFlush = null;

		dbBuffer.abort();
		super.abort();
	}

	private static List<Change> newList() {
		return new ArrayList<CacheService.Change>();
	}
}
