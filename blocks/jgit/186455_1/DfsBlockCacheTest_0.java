		TestRepository<InMemoryRepository> repository = new TestRepository<>(
				r1);
		RevCommit commit = repository.branch("/refs/ref1").commit()
				.add("blob1"
		repository.branch("/refs/ref2").commit().add("blob2"
				.parent(commit).create();

		new DfsGarbageCollector(r1).pack(null);
		DfsBlockCache.reconfigure(new DfsBlockCacheConfig().setBlockSize(512)
				.setBlockLimit(1 << 20).setConcurrencyLevel(1));
		cache = DfsBlockCache.getInstance();
