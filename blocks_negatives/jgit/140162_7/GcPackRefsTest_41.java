			pool.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					refUpdateLockedRef.await();
					gc.packRefs();
					packRefsDone.await();
					return null;
				}
