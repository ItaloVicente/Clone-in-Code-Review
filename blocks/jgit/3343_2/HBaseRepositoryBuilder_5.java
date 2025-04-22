
package org.eclipse.jgit.storage.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.RefData;
import org.eclipse.jgit.storage.dht.RefKey;
import org.eclipse.jgit.storage.dht.RepositoryKey;
import org.eclipse.jgit.storage.dht.spi.Context;
import org.eclipse.jgit.storage.dht.spi.RefTable;

final class HBaseRefTable implements RefTable {
	private static final byte[] QUAL = {};

	private final HTableInterface table;

	private byte[] colTarget;

	HBaseRefTable(HBaseDatabase db) throws DhtException {
		this.table = db.openTable("REF");
		this.colTarget = Bytes.toBytes("target");
	}

	public Map<RefKey
			throws DhtException {
		Scan scan = new Scan();
		scan.setStartRow(RefKey.create(repository
		scan.setStopRow(RefKey.create(repository
		scan.addColumn(colTarget

		Map<RefKey
		try {
			ResultScanner scanner = table.getScanner(scan);
			try {
				Result row;
				while ((row = scanner.next()) != null) {
					RefKey key = RefKey.fromBytes(row.getRow());
					RefData data = RefData.fromBytes(row.value());
					refs.put(key
				}
			} finally {
				scanner.close();
			}
		} catch (IOException err) {
			throw new DhtException(err);
		}
		return refs;
	}

	public boolean compareAndPut(RefKey refKey
			throws DhtException
		byte[] row = refKey.asBytes();
		byte[] old = oldData != RefData.NONE ? oldData.asBytes() : null;
		Put put = new Put(row);
		put.add(colTarget
		try {
			return table.checkAndPut(row
		} catch (IOException err) {
			throw new DhtException(err);
		}
	}

	public boolean compareAndRemove(RefKey refKey
			throws DhtException
		byte[] row = refKey.asBytes();
		byte[] old = oldData.asBytes();
		Delete del = new Delete(row);
		try {
			return table.checkAndDelete(row
		} catch (IOException err) {
			throw new DhtException(err);
		}
	}
}
