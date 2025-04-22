
package org.eclipse.jgit.storage.hbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;
import org.eclipse.jgit.storage.dht.ObjectInfo;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

final class HBaseObjectIndexTable implements ObjectIndexTable {
	private final HBaseDatabase db;

	private final HTableInterface table;

	private final byte[] colInfo;

	HBaseObjectIndexTable(HBaseDatabase db) throws DhtException {
		this.db = db;
		this.table = db.openTable("OBJECT_INDEX");
		this.colInfo = Bytes.toBytes("info");
	}

	public void get(
			Context options
			Set<ObjectIndexKey> objects
			final AsyncCallback<Map<ObjectIndexKey
		final List<Get> ops = new ArrayList<Get>(objects.size());
		for (ObjectIndexKey obj : objects) {
			Get get = new Get(obj.asBytes());
			get.addFamily(colInfo);
			ops.add(get);
		}

		db.getExecutorService().submit(new Runnable() {
			public void run() {
				try {
					callback.onSuccess(findInfo(table.get(ops)));
				} catch (Throwable err) {
					callback.onFailure(new DhtException(err));
				}
			}
		});
	}

	private Map<ObjectIndexKey
		Map<ObjectIndexKey

		map = new HashMap<ObjectIndexKey
		for (Result r : rows) {
			if (r == null || r.isEmpty())
				continue;

			ObjectIndexKey key = ObjectIndexKey.fromBytes(r.getRow());
			Collection<ObjectInfo> list = map.get(key);
			if (list == null) {
				list = new ArrayList<ObjectInfo>(r.size());
				map.put(key
			}

			for (KeyValue kv : r.raw()) {
				ChunkKey k = ChunkKey.fromBytes(kv.getQualifier());
				long time = kv.getTimestamp();
				list.add(ObjectInfo.fromBytes(k
			}
		}

		return map;
	}

	public void add(ObjectIndexKey objId
			throws DhtException {
		HBaseBuffer buf = (HBaseBuffer) buffer;
		ChunkKey chunk = info.getChunkKey();
		Put put = new Put(objId.asBytes());
		put.add(colInfo
		buf.write(table
	}

	public void remove(ObjectIndexKey objId
			throws DhtException {
		HBaseBuffer buf = (HBaseBuffer) buffer;
		Delete del = new Delete(objId.asBytes());
		del.deleteColumns(colInfo
		buf.write(table
	}
}
