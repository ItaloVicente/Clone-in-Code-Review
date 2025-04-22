
package org.eclipse.jgit.storage.dfs;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

final class ReadAheadRejectedExecutionHandler implements
		RejectedExecutionHandler {
	static final ReadAheadRejectedExecutionHandler INSTANCE = new ReadAheadRejectedExecutionHandler();

	private ReadAheadRejectedExecutionHandler() {
	}

	public void rejectedExecution(Runnable r
		((ReadAheadTask.TaskFuture) r).task.abort();
	}
}
