			Future<Result> result = pool.submit(() -> {
				RefUpdate update = new RefDirectoryUpdate(
						(RefDirectory) repo.getRefDatabase()
						repo.exactRef("refs/tags/t")) {
					@Override
					public boolean isForceUpdate() {
						try {
							refUpdateLockedRef.await();
							packRefsDone.await();
						} catch (InterruptedException
								| BrokenBarrierException e) {
							Thread.currentThread().interrupt();
