		Future<Collection<PackFile>> result = executor
				.submit(new Callable<Collection<PackFile>>() {

					@Override
					public Collection<PackFile> call() throws Exception {
						long start = System.currentTimeMillis();
						System.out.println("starting gc");
						latch.countDown();
						Collection<PackFile> r = gc.gc();
						System.out.println("gc took "
								+ (System.currentTimeMillis() - start) + " ms");
						return r;
					}
				});
