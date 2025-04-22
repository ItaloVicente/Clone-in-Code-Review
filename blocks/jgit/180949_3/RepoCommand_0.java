				Random random = new Random();
				for (int i = 0; i < LOCK_FAILURE_MAX_RETRIES - 1; i++) {
					try {
						return commitTreeOnCurrentTip(
							inserter
					} catch (ConcurrentRefUpdateException e) {
						double expBackOff = LOCK_FAILURE_RETRY_DELAY_MILLIS
						int maxJitter = (int) Math.ceil(expBackOff * 0.2);
						Thread.sleep(
								(long) expBackOff + random.nextInt(maxJitter));

						repo.getRefDatabase().refresh();
					}
