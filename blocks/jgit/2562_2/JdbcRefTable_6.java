
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.dht.AsyncCallback;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.ObjectIndexKey;
import org.eclipse.jgit.storage.dht.ObjectInfo;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.StreamingCallback;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

final class JdbcObjectIndexTable implements ObjectIndexTable {
	private final JdbcDatabase db;

	JdbcObjectIndexTable(JdbcDatabase db) {
		this.db = db;
	}

	public void get(Context options
			AsyncCallback<Map<ObjectIndexKey
		db.submit(new Loader(new ArrayList<ObjectIndexKey>(objects)
	}

	private class Loader implements Runnable {
		private final List<ObjectIndexKey> keys;

		private final AsyncCallback<Map<ObjectIndexKey

		private final StreamingCallback<Map<ObjectIndexKey

		private final Map<ObjectIndexKey

		Loader(List<ObjectIndexKey> keys
				AsyncCallback<Map<ObjectIndexKey
			this.keys = keys;
			this.normal = callback;

			if (callback instanceof StreamingCallback<?>) {
				streaming = (StreamingCallback<Map<ObjectIndexKey
				results = null;
			} else {
				streaming = null;
				results = new HashMap<ObjectIndexKey
						keys.size());
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
					select(conn);
					normal.onSuccess(results);
				} catch (SQLException err) {
					normal.onFailure(new DhtException(err));
				}
			} finally {
				db.release(conn);
			}
		}

		private void select(Client conn) throws SQLException {
			String sqlPrefix = "SELECT object_name

			int batchSize = 32;

			for (int keyIdx = 0; keyIdx < keys.size();) {
				int first = keyIdx++;
				RepositoryKey repo = keys.get(first).getRepositoryKey();
				StringBuilder sql = new StringBuilder();
				sql.append(sqlPrefix);
				while (keyIdx < keys.size() && keyIdx - first < batchSize
						&& repo.equals(keys.get(keyIdx).getRepositoryKey())) {
					keyIdx++;
					sql.append("
				}
				sql.append(")");

				Map<ObjectIndexKey
				if (streaming != null)
					out = new HashMap<ObjectIndexKey
				else
					out = results;

				if (sql.toString().endsWith(" IN (?)")) {
					sql.setLength(sql.length() - " IN (?)".length());
					sql.append(" = ?");
				}

				PreparedStatement stmt = conn.prepare(sql.toString());
				stmt.setInt(1
				for (int i = first; i < keyIdx; i++)
					stmt.setString(2 + (i - first)
				ResultSet rs = stmt.executeQuery();
				try {
					while (rs.next()) {
						ObjectId objId = ObjectId.fromString(rs.getString(1));
						ObjectId chunkHash = ObjectId.fromString(rs.getString(2));
						byte[] data = conn.getBytes(rs

						ObjectIndexKey objKey = ObjectIndexKey.create(repo
						Collection<ObjectInfo> list = out.get(objKey);
						if (list == null) {
							list = new ArrayList<ObjectInfo>(2);
							out.put(objKey
						}

						ChunkKey chunk = ChunkKey.create(repo
						list.add(ObjectInfo.fromBytes(chunk
					}
				} finally {
					rs.close();
					conn.release(stmt);
				}

				if (streaming != null)
					streaming.onPartialResult(out);
			}
		}
	}

	public void add(ObjectIndexKey objId
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(Add.OP
	}

	private static class Add extends JdbcBuffer.Task<Add.Item> {
		static final Add OP = new Add();

		static class Item {
			final ObjectIndexKey objectName;

			final ObjectId chunkHash;

			final byte[] info;

			Item(ObjectIndexKey objId
				this.objectName = objId;
				this.chunkHash = info.getChunkKey().getChunkHash();
				this.info = info.asBytes();
			}
		}

		@Override
		int size(Item data) {
			return 3 * 8 + 24 + 20 + 12 + data.info.length;
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("INSERT INTO object_index"
					+ " (repo_id
					+ " VALUES (?
			for (Item item : itemList) {
				stmt.setInt(1
				stmt.setString(2
				stmt.setString(3
				conn.setBytes(stmt
				stmt.addBatch();
			}
			conn.executeBatch(stmt);
		}
	}

	public void remove(ObjectIndexKey objId
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(Remove.OP
	}

	private static class Remove extends JdbcBuffer.Task<Remove.Item> {
		static final Remove OP = new Remove();

		static class Item {
			final ObjectIndexKey objectName;

			final ObjectId chunkHash;

			Item(ObjectIndexKey objId
				this.objectName = objId;
				this.chunkHash = chunk.getChunkHash();
			}
		}

		@Override
		int size(Item data) {
			return 3 * 8 + 24 + 20;
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("DELETE FROM object_index"
					+ " AND chunk_hash = ?");
			for (Item item : itemList) {
				stmt.setInt(1
				stmt.setString(2
				stmt.setString(3
				stmt.addBatch();
			}
			conn.executeBatch(stmt);
		}
	}
}
