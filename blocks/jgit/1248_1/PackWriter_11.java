						if (pool.awaitTermination(60
							break;
					} catch (InterruptedException e) {
						throw new IOException(
								JGitText.get().packingCancelledDuringObjectsWriting);
					}
				}
			}
		} else {
			final CountDownLatch done = new CountDownLatch(myTasks.size());
			for (final DeltaTask task : myTasks) {
				executor.execute(new Runnable() {
					public void run() {
