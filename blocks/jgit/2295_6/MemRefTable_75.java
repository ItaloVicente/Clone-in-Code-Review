
package org.eclipse.jgit.storage.dht.spi.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;
import org.eclipse.jgit.storage.dht.ObjectInfo;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;

final class MemObjectIndexTable implements ObjectIndexTable {
	private final MTable table = new MTable();

	private final ColumnMatcher colInfo = new ColumnMatcher("info:");

	public void get(Context options
			AsyncCallback<Map<ObjectIndexKey
		Map<ObjectIndexKey

		for (ObjectIndexKey objId : objects) {
			for (MTable.Cell cell : table.scanFamily(objId.toBytes()
				Collection<ObjectInfo> info = out.get(objId);
				if (info == null) {
					info = new ArrayList<ObjectInfo>(4);
					out.put(objId
				}

				ChunkKey chunk = ChunkKey.fromShortBytes(objId
						colInfo.suffix(cell.getName()));
				byte[] value = cell.getValue();
				long time = cell.getTimestamp();
				info.add(ObjectInfo.fromBytes(chunk
			}
		}

		callback.onSuccess(out);
	}

	public void add(ObjectIndexKey objId
			throws DhtException {
		ChunkKey chunk = info.getChunkKey();
		table.put(objId.toBytes()
				info.toBytes());
	}

	public void remove(ObjectIndexKey objId
			throws DhtException {
		table.delete(objId.toBytes()
	}
}
