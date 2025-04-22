
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.CachedPackInfo;
import org.eclipse.jgit.storage.dht.CachedPackKey;
import org.eclipse.jgit.storage.dht.ChunkInfo;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

final class JdbcRepositoryTable implements RepositoryTable {
	private final JdbcDatabase db;

	JdbcRepositoryTable(JdbcDatabase db) {
		this.db = db;
	}

	public RepositoryKey nextKey() throws DhtException {
		Client conn = db.get();
		try {
			long next = conn.nextval("repo_id");
			if (Integer.MAX_VALUE < next)
				throw new DhtException(
						JdbcText.get().noMoreRepositoryKeysAvailable);
			return RepositoryKey.create((int) next);
		} catch (SQLException err) {
			throw new DhtException(err);
		} finally {
			db.release(conn);
		}
	}

	public void put(RepositoryKey repo
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(PutChunkInfo.OP
	}

	private static class PutChunkInfo extends
			JdbcBuffer.Task<PutChunkInfo.Item> {
		static final PutChunkInfo OP = new PutChunkInfo();

		static class Item {
			final ChunkKey key;

			final byte[] data;

			Item(ChunkInfo info) {
				this.key = info.getChunkKey();
				this.data = info.asBytes();
			}
		}

		@Override
		int size(Item item) {
			return 8 + 4 + 2 * (8 + 20) + item.data.length;
		}

		@Override
		void run(Client conn
			Map<ChunkKey
			for (Item item : itemList) {
				itemMap.put(item.key
			}
			itemList = new ArrayList<Item>(itemMap.values());

			PreparedStatement stmt = conn.prepare("UPDATE chunk_info"
					+ " AND chunk_hash = ?");
			for (Item item : itemList) {
				conn.setBytes(stmt
				stmt.setInt(2
				stmt.setString(3
				stmt.addBatch();
			}

			int[] status = conn.executeBatch(stmt);
			int updated = 0;
			for (int s : status) {
				if (s == 1)
					updated++;
			}
			if (updated < itemList.size()) {
				stmt = conn.prepare("INSERT INTO chunk_info"
						+ " (repo_id
						+ " VALUES (?
				for (int i = 0; i < itemList.size(); i++) {
					if (status[i] != 0)
						continue;
					Item item = itemList.get(i);
					stmt.setInt(1
					stmt.setString(2
					conn.setBytes(stmt
					stmt.addBatch();
				}
				conn.executeBatch(stmt);
			}
		}
	}

	public void remove(RepositoryKey repo
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(RemoveChunkInfo.OP
	}

	private static class RemoveChunkInfo extends JdbcBuffer.Task<ChunkKey> {
		static final RemoveChunkInfo OP = new RemoveChunkInfo();

		@Override
		int size(ChunkKey data) {
			return 8 + 4 + 8 + 20;
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("DELETE FROM chunk_info"
					+ " AND chunk_hash = ?");
			for (ChunkKey key : keyList) {
				stmt.setInt(1
				stmt.setString(2
				stmt.addBatch();
			}
			conn.executeBatch(stmt);
		}
	}

	public Collection<CachedPackInfo> getCachedPacks(RepositoryKey repo)
			throws DhtException
		Client conn = db.get();
		try {
			String sql = "SELECT pack_data FROM cached_pack WHERE repo_id = ?";
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setInt(1

				ResultSet rs = stmt.executeQuery();
				try {
					List<CachedPackInfo> info = new ArrayList<CachedPackInfo>(2);
					while (rs.next()) {
						info.add(CachedPackInfo.fromBytes(conn.getBytes(rs
					}
					return info;
				} finally {
					rs.close();
				}
			} finally {
				conn.release(stmt);
			}
		} catch (SQLException err) {
			throw new DhtException(err);
		} finally {
			db.release(conn);
		}
	}

	public void put(RepositoryKey repo
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(PutCachedPack.OP
	}

	private static class PutCachedPack extends
			JdbcBuffer.Task<PutCachedPack.Item> {
		static final PutCachedPack OP = new PutCachedPack();

		static class Item {
			final RepositoryKey repo;

			final CachedPackKey key;

			final byte[] data;

			Item(RepositoryKey repo
				this.repo = repo;
				this.key = info.getRowKey();
				this.data = info.asBytes();
			}
		}

		@Override
		int size(Item item) {
			return 8 + 4 + 2 * (8 + 20) + item.data.length;
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("INSERT INTO cached_pack"
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

	public void remove(RepositoryKey repo
			throws DhtException {
		JdbcBuffer buf = (JdbcBuffer) buffer;
		buf.submit(DeleteCachedPack.OP
	}

	private static class DeleteCachedPack extends
			JdbcBuffer.Task<DeleteCachedPack.Item> {
		static final DeleteCachedPack OP = new DeleteCachedPack();

		static class Item {
			final RepositoryKey repo;

			final CachedPackKey key;

			Item(RepositoryKey repo
				this.repo = repo;
				this.key = key;
			}
		}

		@Override
		int size(Item data) {
			return 8 + 4 + 2 * (8 + 20);
		}

		@Override
		void run(Client conn
			PreparedStatement stmt = conn.prepare("DELETE FROM cached_pack"
					+ " AND pack_version = ?");
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
