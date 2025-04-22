
package org.eclipse.jgit.storage.dht.spi.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.storage.dht.CachedPackInfo;
import org.eclipse.jgit.storage.dht.CachedPackKey;
import org.eclipse.jgit.storage.dht.ChunkInfo;
import org.eclipse.jgit.storage.dht.ChunkKey;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.RepositoryTable;
import org.eclipse.jgit.storage.dht.spi.WriteBuffer;
import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;

final class MemRepositoryTable implements RepositoryTable {
	private final AtomicInteger nextId = new AtomicInteger();

	private final MTable table = new MTable();

	private final ColumnMatcher colChunkInfo = new ColumnMatcher("chunk-info:");

	private final ColumnMatcher colCachedPack = new ColumnMatcher(
			"cached-pack:");

	public RepositoryKey nextKey() throws DhtException {
		return RepositoryKey.create(nextId.incrementAndGet());
	}

	public void put(RepositoryKey repo
			throws DhtException {
		table.put(repo.toBytes()
				colChunkInfo.append(info.getChunkKey().toShortBytes())
				info.toBytes());
	}

	public void remove(RepositoryKey repo
			throws DhtException {
		table.delete(repo.toBytes()
	}

	public Collection<CachedPackInfo> getCachedPacks(RepositoryKey repo)
			throws DhtException
		List<CachedPackInfo> out = new ArrayList<CachedPackInfo>(4);
		for (MTable.Cell cell : table.scanFamily(repo.toBytes()
			out.add(CachedPackInfo.fromBytes(repo
		return out;
	}

	public void put(RepositoryKey repo
			throws DhtException {
		table.put(repo.toBytes()
				colCachedPack.append(info.getRowKey().toBytes())
				info.toBytes());
	}

	public void remove(RepositoryKey repo
			throws DhtException {
		table.delete(repo.toBytes()
	}

}
