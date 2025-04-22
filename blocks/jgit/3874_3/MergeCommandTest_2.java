	@Test
	public void testCustomContentMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("side").call();

		MergeResult result = git.merge().include(secondCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE)
				.mergeWith(new MockContentMerger(git.getRepository()))
				.call();
		assertEquals(MergeStatus.MERGED
		assertEquals("custom merge - 1(master):2(master):a(side)\n"
				read(new File(db.getWorkTree()

		assertNull(result.getConflicts());

		assertEquals(RepositoryState.SAFE
	}

