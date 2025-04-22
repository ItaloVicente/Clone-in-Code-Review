
package org.eclipse.jgit.storage.hbase;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.RepositoryName;
import org.eclipse.jgit.storage.dht.spi.RepositoryIndexTable;

class HBaseRepositoryIndexTable implements RepositoryIndexTable {
	private static final byte[] QUAL = {};

	private static final byte[] TRUE = { '1' };

	private final HTableInterface repositoryIndex;

	private final HTableInterface repository;

	private final byte[] colId;

	private final byte[] colName;

	HBaseRepositoryIndexTable(HBaseDatabase db) throws DhtException {
		this.repositoryIndex = db.openTable("REPOSITORY_INDEX");
		this.repository = db.openTable("REPOSITORY");
		this.colId = Bytes.toBytes("id");
		this.colName = Bytes.toBytes("name");
	}

	public RepositoryKey get(RepositoryName name) throws DhtException
			TimeoutException {
		Get get = new Get(name.asBytes());
		get.addColumn(colId

		Result row;
		try {
			row = repositoryIndex.get(get);
		} catch (IOException e) {
			throw new DhtException(e);
		}

		if (row != null && !row.isEmpty())
			return RepositoryKey.fromBytes(row.value());
		return null;
	}

	public void putUnique(RepositoryName name
			throws DhtException
		Put put = new Put(name.asBytes());
		put.add(colId

		boolean ok;
		try {
					name.asBytes()
					colId
					QUAL
					null
					put);
			if (ok) {
				put = new Put(key.asBytes());
				put.add(colName
				repository.put(put);
			}
		} catch (IOException e) {
			throw new DhtException(e);
		}

		if (!ok)
			throw new DhtException("repository exists " + name.asString());
	}
}
