	public void noConcurrencySerializedReads() throws Exception {
		DfsRepositoryDescription repo = new DfsRepositoryDescription("test");
		InMemoryRepository r1 = new InMemoryRepository(repo);
		TestRepository<InMemoryRepository> repository = new TestRepository<>(
				r1);
		RevCommit commit = repository.branch("/refs/ref1").commit()
				.add("blob1", "blob1").create();
		repository.branch("/refs/ref2").commit().add("blob2", "blob2")
				.parent(commit).create();

		new DfsGarbageCollector(r1).pack(null);
