
package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.spi.util.AbstractWriteBuffer;

final class JdbcBuffer extends AbstractWriteBuffer {
	private final ClientPool pool;

	private LinkedHashMap<Task<?>

	JdbcBuffer(ExecutorService executor
		super(executor
		this.pool = pool;
	}

	@SuppressWarnings("unchecked")
	<T> void submit(Task<T> task
		int sz = task.size(data);
		if (add(sz)) {
			if (pending == null)
				pending = new LinkedHashMap<Task<?>

			List<T> list = (List<T>) pending.get(task);
			if (list == null) {
				list = new ArrayList<T>();
				pending.put(task
			}
			list.add(data);
			queued(sz);
		} else {
			List<T> one = Collections.singletonList(data);
			Map map = Collections.singletonMap(task
			start(new TaskRunner(map)
		}
	}

	@Override
	protected void startQueuedOperations(int bufferedByteCount)
			throws DhtException {
		final LinkedHashMap<Task<?>
		pending = null;
		start(new TaskRunner(taskList)
	}

	@Override
	public void abort() throws DhtException {
		pending = null;
		super.abort();
	}

	static abstract class Task<T> {
		abstract int size(T data);

		abstract void run(Client conn
	}

	private final class TaskRunner implements Callable<Void> {
		private final Map<Task<?>

		TaskRunner(Map<Task<?>
			this.taskList = taskList;
		}

		@SuppressWarnings("unchecked")
		public Void call() throws Exception {
			Client conn = pool.get();
			try {
				for (Map.Entry<Task<?>
					Task task = ent.getKey();
					List data = ent.getValue();
					run(conn
				}
			} finally {
				pool.release(conn);
			}
			return null;
		}

		private <T> void run(Client conn
				throws SQLException {
			task.run(conn
		}
	}
}
