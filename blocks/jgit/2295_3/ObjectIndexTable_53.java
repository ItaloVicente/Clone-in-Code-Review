
package org.eclipse.jgit.storage.dht.spi;

public interface Database {
	public RepositoryIndexTable repositoryIndex();

	public RepositoryTable repository();

	public RefTable ref();

	public ObjectIndexTable objectIndex();

	public ChunkTable chunk();

	public WriteBuffer newWriteBuffer();
}
