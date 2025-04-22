
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.storage.dht.DhtException;

public class Client {
	private final Connection conn;

	private final Map<String

	private boolean pending;

	Client(Connection conn) throws SQLException {
		this.conn = conn;

		final int max = 10;
		statementCache = new LinkedHashMap<String
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(Entry<String
				if (size() < max)
					return false;
				try {
					e.getValue().close();
				} catch (SQLException err) {
				}
				return true;
			}
		};

		conn.setAutoCommit(true);
	}

	Statement createStatement() throws SQLException {
		return conn.createStatement();
	}

	boolean hasPending() {
		return pending;
	}

	void clear() {
		try {
			for (PreparedStatement stmt : statementCache.values()) {
				stmt.clearBatch();
				stmt.close();
			}
			pending = false;
		} catch (SQLException err) {
			pending = true;
		}
	}

	PreparedStatement prepare(String sql) throws SQLException {
		PreparedStatement stmt = statementCache.get(sql);
		if (stmt == null) {
			stmt = conn.prepareStatement(sql);
			statementCache.put(sql
		}
		pending = true;
		return stmt;
	}

	int[] executeBatch(PreparedStatement stmt) throws SQLException {
		int[] status = stmt.executeBatch();
		release(stmt);
		return status;
	}

	@SuppressWarnings("unused")
	void release(PreparedStatement stmt) throws SQLException {
		pending = false;
	}

	byte[] getBytes(ResultSet rs
		return rs.getBytes(idx);
	}

	void setBytes(PreparedStatement stmt
			throws SQLException {
		stmt.setBytes(idx
	}

	long nextval(String sequenceName) throws SQLException {
		String sql = "SELECT NEXTVAL('" + sequenceName + "')";
		PreparedStatement stmt = prepare(sql);
		ResultSet rs = stmt.executeQuery();
		try {
			if (rs.next())
				return rs.getLong(1);
			else
				throw new SQLException();
		} finally {
			rs.close();
			release(stmt);
		}
	}

	void close() throws DhtException {
		try {
			try {
				for (PreparedStatement stmt : statementCache.values())
					stmt.close();
			} finally {
				conn.close();
			}
		} catch (SQLException err) {
			throw new DhtException(err);
		}
	}
}
