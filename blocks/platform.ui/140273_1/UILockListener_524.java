			ui = Thread.currentThread();
			try {
				doPendingWork();
			} finally {
				ui = Thread.currentThread();
			}
		}
		return false;
	}

	void addPendingWork(PendingSyncExec work) {
		pendingWork.add(work);
	}
