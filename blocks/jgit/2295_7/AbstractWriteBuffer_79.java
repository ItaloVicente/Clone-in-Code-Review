
package org.eclipse.jgit.storage.dht.spi.memory;

import java.io.IOException;

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

public class MemoryDatabase implements Database {
	private final RepositoryIndexTable repositoryIndex;

	private final RepositoryTable repository;

	private final RefTable ref;

	private final ObjectIndexTable objectIndex;

	private final ChunkTable chunk;

	public MemoryDatabase() {
		repositoryIndex = new MemRepositoryIndexTable();
		repository = new MemRepositoryTable();
		ref = new MemRefTable();
		objectIndex = new MemObjectIndexTable();
		chunk = new MemChunkTable();
	}

	public DhtRepository open(String name) throws IOException {
		return (DhtRepository) new DhtRepositoryBuilder<DhtRepositoryBuilder
				.build();
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
		return new WriteBuffer() {
			public void flush() throws DhtException {
			}

			public void abort() throws DhtException {
			}
		};
	}
}
