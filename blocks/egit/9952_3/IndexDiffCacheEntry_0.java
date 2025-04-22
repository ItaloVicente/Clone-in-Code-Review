				try {
					reloadJobIsInitializing = true;
					waitForWorkspaceLock(monitor);
					lock.lock();
				} finally {
					reloadJobIsInitializing = false;
				}
