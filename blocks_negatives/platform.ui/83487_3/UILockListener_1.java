    /**
     * Should always be called from the UI thread.
     */
    void doPendingWork() {
    	Thread.interrupted();
        Semaphore work;
        while ((work = pendingWork.remove()) != null) {
        	Semaphore oldWork = currentWork;
            try {
                currentWork = work;
                Runnable runnable = work.getRunnable();
                if (runnable != null) {
					runnable.run();
				}

            } finally {
                currentWork = oldWork;
                work.release();
            }
        }
    }
