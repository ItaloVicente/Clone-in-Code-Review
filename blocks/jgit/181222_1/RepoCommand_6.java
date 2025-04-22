				long prevDelay = 0;
				for (int i = 0; i < LOCK_FAILURE_MAX_RETRIES - 1; i++) {
					try {
						return commitTreeOnCurrentTip(
							inserter
					} catch (ConcurrentRefUpdateException e) {
						prevDelay = FileUtils.delay(prevDelay
								LOCK_FAILURE_MIN_RETRY_DELAY_MILLIS
								LOCK_FAILURE_MAX_RETRY_DELAY_MILLIS);
						Thread.sleep(prevDelay);
						repo.getRefDatabase().refresh();
					}
