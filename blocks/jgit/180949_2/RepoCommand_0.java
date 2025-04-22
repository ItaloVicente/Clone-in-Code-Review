				for (int i = 0; i < LOCK_FAILURE_MAX_RETRIES - 1; i++) {
					try {
						return commitTreeOnCurrentTip(inserter
					} catch (ConcurrentRefUpdateException e) {
						repo.getRefDatabase().refresh();
						continue;
					}
