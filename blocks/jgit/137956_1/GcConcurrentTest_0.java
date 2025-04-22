
	@Test
	public void testConcurrentGcAndFetch() throws Exception {
		FileBasedConfig c = repo.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
		c.save();
		SampleDataRepositoryTestCase.copyCGitTestPacks(repo);
		final Git git2 = cloneRepo();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Random random = new SecureRandom();
		for (int i = 0; i < 100; i++) {
			if (random.nextInt(2) > 0) {
				commit(i);
			}
			final int j = i;
			final CountDownLatch latch = new CountDownLatch(2);
			executor.submit(() -> {
				long start = System.currentTimeMillis();
				latch.countDown();
				Collection<PackFile> r = gc.gc();
				PackConfig pc = new PackConfig();
				pc.setCompressionLevel(j % 10);
				gc.setPackConfig(pc);
				PackFile p = r.iterator().next();
				System.out.println("gc\t" + j + " took "
						+ (System.currentTimeMillis() - start) + "ms\tpack "
						+ p.getPackName() + " contains " + p.getObjectCount()
						+ " objects");
				return p;
			});
			executor.submit(() -> {
				latch.countDown();
				int wait = random.nextInt(1000);
				Thread.sleep(wait);
				long start = System.currentTimeMillis();
				FetchResult result = git2.fetch().setRemote("origin").call();
				System.out.println("fetch\t" + j + " took "
						+ (System.currentTimeMillis() - start) + "ms\t"
						+ result.getAdvertisedRef("HEAD") + "
						+ "ms");
				return result;
			});
			latch.await();
		}
		executor.shutdown();
		assertTrue("test didn't finish in 60 sec"
				executor.awaitTermination(60
		executor.shutdownNow();
	}

	private Git cloneRepo() throws IOException
			InvalidRemoteException
		File directory = createTempDirectory("testCloneRepository");
		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		return git2;
	}

	private void commit(int i) throws Exception {
		try (Git git = new Git(repo)) {
			JGitTestUtil.writeTrashFile(repo
					UUID.randomUUID().toString());
			git.add().addFilepattern("Test.txt").call();
			git.commit().setMessage("commit " + i).call();
		}
	}
