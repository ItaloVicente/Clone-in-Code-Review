
package org.eclipse.jgit.storage.dht;

import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.revwalk.ObjectListIterator;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.Database;

final class DhtObjectListIterator extends ObjectListIterator implements
		StreamingCallback<Collection<ObjectListChunk>> {
	private final Database db;

	private final ObjectListInfo info;

	private final DhtReaderOptions options;

	private final MutableObjectId idBuf;

	private final HashMap<ObjectListChunkKey

	private final ArrayList<ObjectListChunkKey> keys;

	private final ReentrantLock lock;

	private final Condition cond;

	private final int highWaterMark;

	private final int lowWaterMark;

	private int nextRequestIndex;

	private int nextUseIndex;

	private int cntReady;

	private int cntLoading;

	private DhtException error;

	private int cType;

	private ObjectListChunk cChunk;

	private int cPtr;

	DhtObjectListIterator(Database db
			DhtReaderOptions options) {
		super(walker);
		this.db = db;
		this.info = info;
		this.options = options;
		this.idBuf = new MutableObjectId();
		this.lock = new ReentrantLock();
		this.cond = lock.newCondition();

		this.highWaterMark = options.getPrefetchDepth();
		int lwm = highWaterMark - 4;
		if (lwm <= 0)
			lwm = highWaterMark / 2;
		lowWaterMark = lwm;

		ready = new HashMap<ObjectListChunkKey
		keys = new ArrayList<ObjectListChunkKey>(info.chunkCount);
		addKeys(info.commits);
		addKeys(info.trees);
		addKeys(info.blobs);
		maybeStartGet();

		cType = OBJ_COMMIT;
		cChunk = null;
	}

	private void addKeys(ObjectListInfo.Segment segment) {
		int id = segment.chunkStart;
		for (int i = 0; i < segment.chunkCount; i++)
			keys.add(info.getChunkKey(id++));
	}

	@Override
	public RevCommit next() throws IOException {
		if (cChunk == null || cPtr == cChunk.size())
			nextListChunk();
		if (cChunk == null || cType != OBJ_COMMIT)
			return null;
		cChunk.getObjectId(idBuf
		return (RevCommit) lookupAny(idBuf
	}

	@Override
	public RevObject nextObject() throws IOException {
		if (cChunk == null || cPtr == cChunk.size())
			nextListChunk();
		if (cChunk == null)
			return null;
		cChunk.getObjectId(idBuf
		return lookupAny(idBuf
	}

	@Override
	public int getPathHashCode() {
		return cChunk.getPathHashCode(cPtr - 1);
	}

	@Override
	public void release() {
	}

	private void nextListChunk() throws DhtException {
		lock.lock();
		try {
			if (nextUseIndex == keys.size()) {
				cChunk = null;
				return;
			}

			ObjectListChunkKey key = keys.get(nextUseIndex++);
			while (error == null && !ready.containsKey(key)) {
				try {
					Timeout t = options.getObjectListChunkTimeout();
					if (!cond.await(t.getTime()
						throw new DhtTimeoutException(MessageFormat.format(
								DhtText.get().timeoutWaitingForObjectList
					}
				} catch (InterruptedException e) {
					throw new DhtTimeoutException(e);
				}
			}
			if (error != null)
				throw error;

			cChunk = ready.remove(key);
			cPtr = 0;
			cType = key.getObjectType();
			cntReady--;

			maybeStartGet();
		} finally {
			lock.unlock();
		}
	}

	private void maybeStartGet() {
		if (nextRequestIndex == keys.size())
			return;

		if (lowWaterMark < cntReady + cntLoading)
			return;

		boolean first = nextRequestIndex == 0;
		HashSet<ObjectListChunkKey> toLoad = new HashSet<ObjectListChunkKey>();
		while (cntReady + cntLoading + toLoad.size() < highWaterMark
				&& nextRequestIndex < keys.size()) {
			toLoad.add(keys.get(nextRequestIndex++));

			if (first)
				break;
		}
		cntLoading += toLoad.size();

		if (!toLoad.isEmpty() && error == null)
			db.objectList().get(Context.LOCAL

		if (first)
			maybeStartGet();
	}

	public void onPartialResult(Collection<ObjectListChunk> res) {
		lock.lock();
		try {
			for (ObjectListChunk listChunk : res)
				ready.put(listChunk.getRowKey()
			cntReady += res.size();
			cntLoading -= res.size();
			cond.signal();
		} finally {
			lock.unlock();
		}
	}

	public void onSuccess(Collection<ObjectListChunk> result) {
		if (result != null && !result.isEmpty())
			onPartialResult(result);
	}

	public void onFailure(DhtException asyncError) {
		lock.lock();
		try {
			if (error == null)
				error = asyncError;
			cond.signal();
		} finally {
			lock.unlock();
		}
	}
}
