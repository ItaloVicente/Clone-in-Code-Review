					reloadJobIsInitializing = true;
					waitForWorkspaceLock(monitor);
					lock.lock();
				} finally {
					reloadJobIsInitializing = false;
				}
				try {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					parallelism.acquire();
