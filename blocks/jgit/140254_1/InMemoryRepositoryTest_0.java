
	@Test
	public void sha1ToTip_ref() throws Exception {
		InMemoryRepository repo = new InMemoryRepository(
				new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			RevCommit commit = git.branch("master").commit()
					.message("first commit").create();

			Set<Ref> tipsWithSha1 = repo.getRefDatabase()
					.getTipsWithSha1(commit.getId());
			assertEquals(1
			Ref ref = tipsWithSha1.iterator().next();
			assertEquals(ref.getName()
			assertEquals(commit.getId()
		}
	}

	@Test
	public void sha1ToTip_annotatedTag() throws Exception {
		InMemoryRepository repo = new InMemoryRepository(
				new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			RevCommit commit = git.commit()
					.message("first commit").create();
			RevTag tagObj = git.tag("v0.1"
			git.update("refs/tags/v0.1"
			Set<Ref> tipsWithSha1 = repo.getRefDatabase()
					.getTipsWithSha1(commit.getId());
			assertEquals(1
			Ref ref = tipsWithSha1.iterator().next();
			assertEquals(ref.getName()
			assertEquals(commit.getId()
		}
	}

	@Test
	public void sha1ToTip_tag() throws Exception {
		InMemoryRepository repo = new InMemoryRepository(
				new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			RevCommit commit = git.commit().message("first commit").create();
			git.update("refs/tags/v0.2"
			Set<Ref> tipsWithSha1 = repo.getRefDatabase()
					.getTipsWithSha1(commit.getId());
			assertEquals(1
			Ref ref = tipsWithSha1.iterator().next();
			assertEquals(ref.getName()
			assertEquals(commit.getId()
		}
	}
