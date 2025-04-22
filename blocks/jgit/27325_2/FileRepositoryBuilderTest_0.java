
	@Test
	public void shouldAllowIndependentlyConfiguredWindowCache()
			throws IOException {
		Repository srcRepo = createBareRepository();
		TestRng rng = new TestRng(JGitTestUtil.getName());
		ObjectInserter inserter = srcRepo.getObjectDatabase().newInserter();
		ObjectId largeObject = inserter.insert(OBJ_BLOB
		ObjectId smallObject = inserter.insert(OBJ_BLOB
		inserter.release();

		FileRepositoryBuilder builder =
			new FileRepositoryBuilder().setGitDir(srcRepo.getDirectory());
		Repository repoWithHighThreshold =
			builder.setWindowCache(configForStreamingThreshold(8192)).build();
		Repository repoWithLowThreshold =
			builder.setWindowCache(configForStreamingThreshold(1024)).build();

		assertCanRead(repoWithHighThreshold
		assertCanRead(repoWithHighThreshold

		assertCanRead(repoWithLowThreshold
		assertCanNotRead(repoWithLowThreshold
	}

	private void assertCanNotRead(Repository repo
		try {
			assertCanRead(repo
			fail();
		} catch (LargeObjectException loe) {
		}
	}

	private void assertCanRead(Repository repo
		repo.getObjectDatabase().newReader().open(objectId).getBytes();
	}

	private static WindowCacheConfig configForStreamingThreshold(int limit) {
		WindowCacheConfig cfg = new WindowCacheConfig();
		cfg.setStreamFileThreshold(limit);
		return cfg;
	}

