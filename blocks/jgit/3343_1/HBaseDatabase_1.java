
package org.eclipse.jgit.storage.hbase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.ChunkMeta;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

final class HBaseChunkTable implements ChunkTable {
	private final HBaseDatabase db;

	private final HTableInterface table;

	private final byte[] colChunk;

	private final byte[] colIndex;

	private final byte[] colMeta;

	HBaseChunkTable(HBaseDatabase db) throws DhtException {
		this.db = db;
		this.table = db.openTable("CHUNK");
		this.colChunk = Bytes.toBytes("chunk");
		this.colIndex = Bytes.toBytes("index");
		this.colMeta = Bytes.toBytes("meta");
	}

	public void get(Context options
			final AsyncCallback<Collection<PackChunk.Members>> callback) {
		final List<Get> ops = new ArrayList<Get>(keys.size());
		for (ChunkKey key : keys) {
			Get get = new Get(key.asBytes());
			get.addFamily(colChunk);
			get.addFamily(colIndex);
			get.addFamily(colMeta);
			ops.add(get);
		}

		db.getExecutorService().submit(new Runnable() {
			public void run() {
				try {
					callback.onSuccess(parseChunks(table.get(ops)));
				} catch (Throwable err) {
					callback.onFailure(new DhtException(err));
				}
			}
		});
	}

	private List<PackChunk.Members> parseChunks(Result[] rows)
			throws DhtException {
		List<PackChunk.Members> chunkList =
			new ArrayList<PackChunk.Members>(rows.length);

		for (Result r : rows) {
			if (r == null || r.isEmpty())
				continue;

			ChunkKey key = ChunkKey.fromBytes(r.getRow());
			PackChunk.Members m = new PackChunk.Members();
			m.setChunkKey(key);

			byte[] chunk = r.getValue(colChunk
			byte[] index = r.getValue(colIndex
			byte[] meta = r.getValue(colMeta

			if (chunk != null)
				m.setChunkData(chunk);
			if (index != null)
				m.setChunkIndex(index);
			if (meta != null)
				m.setMeta(ChunkMeta.fromBytes(key

			chunkList.add(m);
		}
		return chunkList;
	}

	public void getMeta(Context options
			final AsyncCallback<Collection<ChunkMeta>> callback) {
		final List<Get> ops = new ArrayList<Get>(keys.size());
		for (ChunkKey key : keys) {
			Get get = new Get(key.asBytes());
			get.addFamily(colMeta);
			ops.add(get);
		}

		db.getExecutorService().submit(new Runnable() {
			public void run() {
				try {
					callback.onSuccess(parseMeta(table.get(ops)));
				} catch (Throwable err) {
					callback.onFailure(new DhtException(err));
				}
			}
		});
	}

	private List<ChunkMeta> parseMeta(Result[] rows)
			throws DhtException {
		List<ChunkMeta> metaList = new ArrayList<ChunkMeta>(rows.length);
		for (Result r : rows) {
			if (r == null || r.isEmpty())
				continue;

			ChunkKey key = ChunkKey.fromBytes(r.getRow());
			byte[] meta = r.getValue(colMeta
			metaList.add(ChunkMeta.fromBytes(key
		}
		return metaList;
	}

	public void put(PackChunk.Members chunk
			throws DhtException {
		HBaseBuffer buf = (HBaseBuffer) buffer;
		Put put = new Put(chunk.getChunkKey().asBytes());

		if (chunk.getChunkData() != null)
			put.add(colChunk

		if (chunk.getChunkIndex() != null)
			put.add(colIndex

		if (chunk.getMeta() != null)
			put.add(colMeta

		buf.write(table
	}

	public void remove(ChunkKey key
		HBaseBuffer buf = (HBaseBuffer) buffer;
		buf.write(table
	}
}
