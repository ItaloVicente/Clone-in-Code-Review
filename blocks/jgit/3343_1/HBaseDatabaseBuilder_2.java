
package org.eclipse.jgit.storage.hbase;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;

public class HBaseDatabase implements Database {
	private final Configuration configuration;

	private final String schemaPrefix;

	private final ExecutorService executors;

	private final HBaseRepositoryIndexTable repositoryIndex;

	private final HBaseRepositoryTable repository;

	private final HBaseRefTable ref;

	private final HBaseChunkTable chunk;

	private final HBaseObjectIndexTable objectIndex;

	HBaseDatabase(HBaseDatabaseBuilder builder) throws DhtException {
		this.configuration = builder.getConfiguration();
		this.schemaPrefix = builder.getSchemaPrefix();
		this.executors = builder.getExecutorService();

		repositoryIndex = new HBaseRepositoryIndexTable(this);
		repository = new HBaseRepositoryTable(this);
		ref = new HBaseRefTable(this);
		chunk = new HBaseChunkTable(this);
		objectIndex = new HBaseObjectIndexTable(this);
	}

	public void shutdown() {
		HConnectionManager.deleteConnection(configuration
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

	public ChunkTable chunk() {
		return chunk;
	}

	public ObjectIndexTable objectIndex() {
		return objectIndex;
	}

	public WriteBuffer newWriteBuffer() {
		return new HBaseBuffer(this
	}

	HTableInterface openTable(String tableName) throws DhtException {
		if (schemaPrefix != null && schemaPrefix.length() > 0)
			tableName = schemaPrefix + "." + tableName;
		try {
			return new HTable(getConfiguration()
		} catch (IOException e) {
			throw new DhtException("Cannot open table " + tableName
		}
	}

	Configuration getConfiguration() {
		return configuration;
	}

	ExecutorService getExecutorService() {
		return executors;
	}
}
