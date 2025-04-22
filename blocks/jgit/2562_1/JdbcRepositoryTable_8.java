
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtText;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;

final class JdbcRepositoryIndexTable implements RepositoryIndexTable {
	private final JdbcDatabase db;

	JdbcRepositoryIndexTable(JdbcDatabase db) {
		this.db = db;
	}

	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException {
		Client conn = db.get();
		try {
			String sql = "SELECT repo_id FROM repository_index WHERE repo_name = ?";
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setString(1
				ResultSet rs = stmt.executeQuery();
				try {
					if (rs.next())
						return RepositoryKey.fromInt(rs.getInt(1));
					else
						return null;
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

	public void putUnique(RepositoryName name
			throws DhtException
		Client conn = db.get();
		try {
			String sql = "INSERT INTO repository_index (repo_name
			PreparedStatement stmt = conn.prepare(sql);
			try {
				stmt.setString(1
				stmt.setInt(2
				if (stmt.executeUpdate() != 1) {
					throw new DhtException(MessageFormat.format(
							DhtText.get().repositoryAlreadyExists
							name.asString()));
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
}
