	void doPendingWork() {
		Thread.interrupted();
		PendingSyncExec work;
		while ((work = pendingWork.remove()) != null) {
			PendingSyncExec oldWork = currentWork;
			try {
				currentWork = work;
				work.run();
			} finally {
				currentWork = oldWork;
			}
		}
	}
