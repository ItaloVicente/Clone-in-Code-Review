
package org.eclipse.jgit.storage.dht.spi.cache;

import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.storage.dht.spi.ChunkTable;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.dht.spi.ObjectIndexTable;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;

public class CacheDatabase implements Database {
	private final Database database;

	private final ExecutorService executorService;

	private final CacheService client;

	private final CacheOptions options;

	private final CacheRepositoryIndexTable repositoryIndex;

	private final CacheRepositoryTable repository;

	private final CacheRefTable ref;

	private final CacheObjectIndexTable objectIndex;

	private final CacheChunkTable chunk;

	public CacheDatabase(Database database
			CacheService client
		this.database = database;
		this.executorService = executor;
		this.client = client;
		this.options = options;

		repositoryIndex = new CacheRepositoryIndexTable(database
				.repositoryIndex()

		repository = new CacheRepositoryTable(database.repository()
		ref = new CacheRefTable(database.ref()
		objectIndex = new CacheObjectIndexTable(database.objectIndex()
		chunk = new CacheChunkTable(database.chunk()
	}

	public Database getDatabase() {
		return database;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public CacheService getClient() {
		return client;
	}

	public CacheOptions getOptions() {
		return options;
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

	public CacheBuffer newWriteBuffer() {
		return new CacheBuffer(database.newWriteBuffer()
	}
}
