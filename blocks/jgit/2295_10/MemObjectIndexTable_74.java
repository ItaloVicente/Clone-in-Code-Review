
package org.eclipse.jgit.storage.dht.spi.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.ChunkMeta;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;

final class MemChunkTable implements ChunkTable {
	private final MTable table = new MTable();

	private final ColumnMatcher colData = new ColumnMatcher("data");

	private final ColumnMatcher colIndex = new ColumnMatcher("index");

	private final ColumnMatcher colMeta = new ColumnMatcher("meta");

	public void get(Context options
			AsyncCallback<Collection<PackChunk.Members>> callback) {
		int cnt = keys.size();
		List<PackChunk.Members> out = new ArrayList<PackChunk.Members>(cnt);

		for (ChunkKey chunk : keys) {
			byte[] row = chunk.toBytes();
			MTable.Cell cell;

			cell = table.get(row
			if (cell == null)
				continue;

			PackChunk.Members m = new PackChunk.Members();
			m.setChunkKey(chunk);
			m.setChunkData(cell.getValue());

			cell = table.get(row
			if (cell != null)
				m.setChunkIndex(cell.getValue());

			cell = table.get(row
			if (cell != null)
				m.setMeta(ChunkMeta.fromBytes(chunk

			out.add(m);
		}

		callback.onSuccess(out);
	}

	public void put(PackChunk.Members chunk
			throws DhtException {
		byte[] row = chunk.getChunkKey().toBytes();

		if (chunk.getChunkData() != null)
			table.put(row

		if (chunk.getChunkIndex() != null)
			table.put(row

		if (chunk.getMeta() != null)
			table.put(row
	}

	public void remove(ChunkKey key
		table.deleteRow(key.toBytes());
	}
}
