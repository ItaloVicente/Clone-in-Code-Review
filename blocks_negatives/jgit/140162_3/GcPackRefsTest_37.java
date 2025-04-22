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
						}
					};
					update.setForceUpdate(true);
					update.setNewObjectId(b);
					return update.update();
				}
			});

			pool.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					refUpdateLockedRef.await();
					gc.packRefs();
					packRefsDone.await();
					return null;
				}
			});
