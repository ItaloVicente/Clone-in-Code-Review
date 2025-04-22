
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtRepository;
import org.eclipse.jgit.storage.dht.DhtRepositoryBuilder;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public class JdbcDatabase implements Database {
	private final ExecutorService executors;

	private final ClientPool pool;

	private final JdbcRepositoryIndexTable repositoryIndex;

	private final JdbcRepositoryTable repository;

	private final JdbcRefTable ref;

	private final JdbcObjectIndexTable objectIndex;

	private final JdbcChunkTable chunk;

	public JdbcDatabase(ExecutorService executors
		this.executors = executors;
		this.pool = pool;

		repositoryIndex = new JdbcRepositoryIndexTable(this);
		repository = new JdbcRepositoryTable(this);
		ref = new JdbcRefTable(this);
		objectIndex = new JdbcObjectIndexTable(this);
		chunk = new JdbcChunkTable(this);
	}

	public void shutdown() {
		pool.shutdown();
	}

	public DhtRepository open(String name) throws IOException {
		return (DhtRepository) new DhtRepositoryBuilder<DhtRepositoryBuilder
				.build();
	}

	public void initalizeSchema() throws SQLException
		InputStream in = getClass().getResourceAsStream("git_schema.sql");
		BufferedReader br = new BufferedReader(new InputStreamReader(in
				"UTF-8"));
		StringBuilder buf = new StringBuilder();

		Client conn = get();
		Statement stmt = conn.createStatement();
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("--") || line.length() == 0)
				continue;
			if (0 < buf.length())
				buf.append("\n");
			buf.append(line);
			if (buf.charAt(buf.length() - 1) == ';') {
				stmt.execute(buf.toString());
				buf.setLength(0);
			}
		}
		stmt.close();
		release(conn);
	}

	public RepositoryIndexTable repositoryIndex() {
		return repositoryIndex;
	}

	public RepositoryTable repository() {
		return repository;
	}

	public RefTable ref() {
		return ref;
	}

	public ObjectIndexTable objectIndex() {
		return objectIndex;
	}

	public ChunkTable chunk() {
		return chunk;
	}

	public WriteBuffer newWriteBuffer() {
		return new JdbcBuffer(executors
	}

	Client get() throws DhtException {
		return pool.get();
	}

	void release(Client conn) {
		pool.release(conn);
	}

	void submit(Runnable task) {
		executors.submit(task);
	}
}
