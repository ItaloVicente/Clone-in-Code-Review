
	@Test
	public void testInterruptGc() throws Exception {
		FileBasedConfig c = repo.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
		c.save();
		SampleDataRepositoryTestCase.copyCGitTestPacks(repo);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		final CountDownLatch latch = new CountDownLatch(1);
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
		try {
			latch.await();
			Thread.sleep(5);
			executor.shutdownNow();
			result.get();
			fail("thread wasn't interrupted");
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof CancelledException) {
				assertEquals(JGitText.get().operationCanceled
						cause.getMessage());
			} else if (cause instanceof IOException) {
				Throwable cause2 = cause.getCause();
				assertTrue(cause2 instanceof InterruptedException
						|| cause2 instanceof ExecutionException);
			} else {
				fail("unexpected exception " + e);
			}
		}
	}
