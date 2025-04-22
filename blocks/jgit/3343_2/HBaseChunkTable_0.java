
package org.eclipse.jgit.storage.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtTimeoutException;
import org.eclipse.jgit.storage.dht.spi.util.AbstractWriteBuffer;

final class HBaseBuffer extends AbstractWriteBuffer {
	private Map<HTableInterface

	HBaseBuffer(HBaseDatabase db
		super(db.getExecutorService()
	}

	void write(HTableInterface table
		int sz = (int) row.heapSize();
		if (add(sz)) {
			queue(table
		} else {
			TableBuffer b = new TableBuffer(table);
			b.add(row
			b.startInBackground();
		}
	}

	void write(HTableInterface table
		int sz = (int) row.getRow().length;
		add(sz);
		queue(table
	}

	private void queue(HTableInterface table
			throws DhtException {
		if (queued == null)
			queued = new HashMap<HTableInterface
		TableBuffer b = queued.get(table);
		if (b == null) {
			b = new TableBuffer(table);
			queued.put(table
		}
		b.add(row
		queued(sz);
	}

	@Override
	protected void startQueuedOperations(int bufferedByteCount)
			throws DhtException {
		for (TableBuffer b : queued.values())
			b.startInBackground();
		queued = null;
	}

	@Override
	public void abort() throws DhtException {
		queued = null;
		super.abort();
	}

	private class TableBuffer {
		final HTableInterface table;

		final List<Row> actions;

		int size;

		TableBuffer(HTableInterface table) {
			this.table = table;
			this.actions = new ArrayList<Row>();
		}

		void add(Row row
			actions.add(row);
			size += sz;
		}

		void startInBackground() throws DhtException {
			start(new Callable<Void>() {
				public Void call() throws Exception {
					try {
						table.batch(actions);
						return null;
					} catch (IOException err) {
						throw new DhtException(err);
					} catch (InterruptedException err) {
						throw new DhtTimeoutException(err);
					}
				}
			}
		}
	}
}
