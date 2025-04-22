			Future<Result> result = pool.submit(new Callable<Result>() {

				@Override
				public Result call() throws Exception {
					RefUpdate update = new RefDirectoryUpdate(
							(RefDirectory) repo.getRefDatabase(),
							repo.exactRef("refs/tags/t")) {
						@Override
						public boolean isForceUpdate() {
							try {
								refUpdateLockedRef.await();
								packRefsDone.await();
							} catch (InterruptedException | BrokenBarrierException e) {
								Thread.currentThread().interrupt();
							}
							return super.isForceUpdate();
