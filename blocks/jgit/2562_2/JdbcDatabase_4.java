
package org.eclipse.jgit.storage.dht.spi.jdbc;

import static java.util.Collections.singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.ChunkMeta;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.PackChunk;
import org.eclipse.jgit.storage.dht.PackChunk.Members;
import org.eclipse.jgit.storage.dht.StreamingCallback;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

final class JdbcChunkTable implements ChunkTable {
	private final JdbcDatabase db;

	JdbcChunkTable(JdbcDatabase db) {
		this.db = db;
	}

	public void get(Context options
			AsyncCallback<Collection<PackChunk.Members>> callback) {
		db.submit(new Loader(keys
	}

	private class Loader implements Runnable {
		private final Set<ChunkKey> keys;

		private final AsyncCallback<Collection<PackChunk.Members>> normal;

		private final StreamingCallback<Collection<PackChunk.Members>> streaming;

		private final List<PackChunk.Members> results;

		Loader(Set<ChunkKey> keys
			this.keys = keys;
			this.normal = callback;

			if (callback instanceof StreamingCallback<?>) {
				streaming = (StreamingCallback<Collection<Members>>) callback;
				results = null;
			} else {
				streaming = null;
				results = new ArrayList<PackChunk.Members>(keys.size());
			}
		}

		public void run() {
			Client conn;
			try {
				conn = db.get();
			} catch (DhtException err) {
				normal.onFailure(err);
				return;
			}
			try {
				try {
					for (ChunkKey key : keys) {
						PackChunk.Members chunk = load(conn
						if (chunk == null)
							continue;
						if (streaming != null)
							streaming.onPartialResult(singleton(chunk));
						else
							results.add(chunk);
					}
					normal.onSuccess(results);
				} catch (SQLException err) {
					normal.onFailure(new DhtException(err));
				}
			} finally {
				db.release(conn);
			}
		}

		private PackChunk.Members load(Client conn
				throws SQLException {
			String sql = "SELECT chunk_data
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setInt(1
				stmt.setString(2
				ResultSet rs = stmt.executeQuery();
				try {
					if (!rs.next())
						return null;

					PackChunk.Members m = new PackChunk.Members();
					m.setChunkKey(key);
					m.setChunkData(conn.getBytes(rs
					m.setChunkIndex(conn.getBytes(rs

					byte[] meta = conn.getBytes(rs
					if (meta != null)
						m.setMeta(ChunkMeta.fromBytes(key
					return m;
				} finally {
					rs.close();
				}
			} finally {
				conn.release(stmt);
			}
		}
	}

	public void put(PackChunk.Members chunk
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(Put.OP
	}

	private static class Put extends JdbcBuffer.Task<PackChunk.Members> {
		static final Put OP = new Put();

		@Override
		int size(PackChunk.Members data) {
			int sz = 24;
			if (data.getChunkData() != null)
				sz += data.getChunkData().length;
			if (data.getChunkIndex() != null)
				sz += data.getChunkIndex().length;
			if (data.getMeta() != null)
			return sz;
		}

		@Override
		void run(Client conn
				throws SQLException {
			for (PackChunk.Members chunk : chunkList) {
				if (chunk.getChunkData() != null)
					insert(conn
				else
					update(conn
			}
		}

		private void insert(Client conn
				throws SQLException {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO chunk (repo_id
			if (chunk.getChunkIndex() != null)
				sql.append("
			if (chunk.getMeta() != null)
				sql.append("
			sql.append(") VALUES (?
			if (chunk.getChunkIndex() != null)
				sql.append("
			if (chunk.getMeta() != null)
				sql.append("
			sql.append(")");

			PreparedStatement stmt = conn.prepare(sql.toString());
			stmt.setInt(1
			stmt.setString(2
			conn.setBytes(stmt

			int col = 4;
			if (chunk.getChunkIndex() != null)
				conn.setBytes(stmt
			if (chunk.getMeta() != null)
				conn.setBytes(stmt
			if (stmt.executeUpdate() != 1)
				throw new SQLException(JdbcText.get().chunkInsertFailed);
			conn.release(stmt);
		}

		private void update(Client conn
				throws SQLException {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE chunk SET");
			if (chunk.getChunkIndex() != null)
				sql.append(" chunk_index = ?");
			if (chunk.getMeta() != null) {
				if (chunk.getChunkIndex() != null)
					sql.append("
			}
			sql.append(" WHERE repo_id = ? AND chunk_hash = ?");

			PreparedStatement stmt = conn.prepare(sql.toString());
			int col = 1;
			if (chunk.getChunkIndex() != null)
				conn.setBytes(stmt
			if (chunk.getMeta() != null)
				conn.setBytes(stmt
			stmt.setInt(col++
			stmt.setString(col++
			if (stmt.executeUpdate() != 1)
				throw new SQLException(JdbcText.get().chunkUpdateFailed);
			conn.release(stmt);
		}
	}

	public void remove(ChunkKey key
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(Remove.OP
	}

	private static class Remove extends JdbcBuffer.Task<ChunkKey> {
		static final Remove OP = new Remove();

		@Override
		int size(ChunkKey data) {
			return 8 + 4 + 8 + 20;
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("DELETE FROM chunk"
					+ " AND chunk_hash = ?");
			for (ChunkKey key : keyList) {
				stmt.setInt(1
				stmt.setString(2
				stmt.addBatch();
			}
			conn.executeBatch(stmt);
		}
	}
}
