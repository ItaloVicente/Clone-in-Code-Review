
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.util.Base64;

final class JdbcRefTable implements RefTable {
	private final JdbcDatabase db;

	JdbcRefTable(JdbcDatabase db) {
		this.db = db;
	}

	public Map<RefKey
			throws DhtException
		Client conn = db.get();
		try {
			String sql = "SELECT ref_name
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setInt(1

				ResultSet rs = stmt.executeQuery();
				try {
					Map<RefKey
					while (rs.next()) {
						String name = rs.getString(1);
						String encData = rs.getString(2);
						byte[] rawData = Base64.decode(encData);
						RefData data = RefData.fromBytes(rawData);
						all.put(RefKey.create(repo
					}
					return all;
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

	public boolean compareAndPut(RefKey refKey
			throws DhtException
		Client conn = db.get();
		try {
			String sql;

			if (oldData == RefData.NONE)
				sql = "INSERT INTO ref (repo_id
			else
				sql = "UPDATE ref SET ref_data = ? WHERE repo_id = ? AND ref_name = ? AND ref_data = ?";

			PreparedStatement stmt = conn.prepare(sql);
			try {
				if (oldData == RefData.NONE) {
					stmt.setInt(1
					stmt.setString(2
					stmt.setString(3
				} else {
					stmt.setString(1
					stmt.setInt(2
					stmt.setString(3
					stmt.setString(4
				}
				return 1 == stmt.executeUpdate();
			} finally {
				conn.release(stmt);
			}
		} catch (SQLException err) {
			throw new DhtException(err);
		} finally {
			db.release(conn);
		}
	}

	public boolean compareAndRemove(RefKey refKey
			throws DhtException
		Client conn = db.get();
		try {
			String sql = "DELETE FROM ref WHERE repo_id = ? AND ref_name = ? AND ref_data = ?";
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setInt(1
				stmt.setString(2
				stmt.setString(3
				return 1 == stmt.executeUpdate();
			} finally {
				conn.release(stmt);
			}
		} catch (SQLException err) {
			throw new DhtException(err);
		} finally {
			db.release(conn);
		}
	}
}
