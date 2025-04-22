
	private InMemoryRepository createRepoWithBitmap(String repoName)
			throws Exception {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				repoName);
		InMemoryRepository repo = new InMemoryRepository(repoDesc);
		try (TestRepository<InMemoryRepository> repository = new TestRepository<>(
				repo)) {
			RevCommit commit = repository.branch("/refs/ref1" + repoName)
					.commit().add("blob1"
			repository.branch("/refs/ref2" + repoName).commit()
					.add("blob2"
		}
		new DfsGarbageCollector(repo).pack(null);
		return repo;
	}

	private void asyncRun(Callable<?> call) {
		pool.execute(() -> {
			try {
				call.call();
			} catch (Exception e) {
			}
		});
	}

	private void waitForExecutorPoolTermination() throws Exception {
		pool.shutdown();
		pool.awaitTermination(500
		assertTrue("Threads did not complete
				pool.isTerminated());
	}
