
package org.eclipse.jgit.storage.dht.spi.memory;

import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtText;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;
import org.eclipse.jgit.storage.dht.spi.memory.MemTable.Cell;
import org.eclipse.jgit.storage.dht.spi.util.ColumnMatcher;

final class MemRepositoryIndexTable implements RepositoryIndexTable {
	private final MemTable table = new MemTable();

	private final ColumnMatcher colId = new ColumnMatcher("id");

	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException {
		Cell cell = table.get(name.asBytes()
		if (cell == null)
			return null;
		return RepositoryKey.fromBytes(cell.getValue());
	}

	public void putUnique(RepositoryName name
			throws DhtException
				name.asBytes()
				colId.name()
				null
				key.asBytes());
		if (!ok)
			throw new DhtException(MessageFormat.format(
					DhtText.get().repositoryAlreadyExists
	}
}
