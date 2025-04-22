
package org.eclipse.jgit.storage.dht.spi.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.RefTable;
import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;

final class MemRefTable implements RefTable {
	private final MemTable table = new MemTable();

	private final ColumnMatcher colRef = new ColumnMatcher("ref:");

	public Map<RefKey
			throws DhtException
		Map<RefKey
		for (MemTable.Cell cell : table.scanFamily(repository.asBytes()
			RefKey ref = RefKey.fromBytes(colRef.suffix(cell.getName()));
			RefData val = RefData.fromBytes(cell.getValue());
			out.put(ref
		}
		return out;
	}

	public boolean compareAndPut(RefKey refKey
			throws DhtException
		RepositoryKey repo = refKey.getRepositoryKey();
				repo.asBytes()
				colRef.append(refKey.asBytes())
				oldData != RefData.NONE ? oldData.asBytes() : null
				newData.asBytes());
	}

	public boolean compareAndRemove(RefKey refKey
			throws DhtException
		RepositoryKey repo = refKey.getRepositoryKey();
				repo.asBytes()
				colRef.append(refKey.asBytes())
				oldData != RefData.NONE ? oldData.asBytes() : null
				null);
	}
}
