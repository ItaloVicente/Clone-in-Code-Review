
	@Test
	public void dontPackHEAD_nonBare() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/side");
		RevCommit first = bb.commit().add("A"
		bb.commit().add("A"
		Git git = Git.wrap(repo);

		assertEquals(repo.getRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.getRef("HEAD").getTarget().getObjectId());
		gc.packRefs();
		assertSame(repo.getRef("HEAD").getStorage()
		assertEquals(repo.getRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.getRef("HEAD").getTarget().getObjectId());

		git.checkout().setName("refs/heads/side").call();
		gc.packRefs();
		assertSame(repo.getRef("HEAD").getStorage()

		git.checkout().setName(first.getName()).call();
		gc.packRefs();
		assertSame(repo.getRef("HEAD").getStorage()
	}

	@Test
	public void dontPackHEAD_bare() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/side");
		bb.commit().add("A"
		RevCommit second = bb.commit().add("A"

		FileBasedConfig cfg = repo.getConfig();
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_BARE
		cfg.save();
		Git git = Git.open(repo.getDirectory());
		repo = (FileRepository) git.getRepository();

		assertEquals(repo.getRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.getRef("HEAD").getTarget().getObjectId());
		gc.packRefs();
		assertSame(repo.getRef("HEAD").getStorage()
		assertEquals(repo.getRef("HEAD").getTarget().getName()
				"refs/heads/master");
		assertNull(repo.getRef("HEAD").getTarget().getObjectId());

		repo.updateRef(Constants.HEAD).link("refs/heads/side");
		gc.packRefs();
		assertSame(repo.getRef("HEAD").getStorage()
		assertEquals(repo.getRef("HEAD").getTarget().getObjectId()
				second.getId());
	}
