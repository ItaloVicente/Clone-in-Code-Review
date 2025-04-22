
package org.eclipse.jgit.storage.dht.spi;

public interface Database {
	public RepositoryTable repositories();

	public RefTable refs();

	public ObjectIndexTable objectIndex();

	public ChunkTable chunks();

	public WriteBuffer newWriteBuffer();
}
