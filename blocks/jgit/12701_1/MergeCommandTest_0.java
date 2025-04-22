	@Test
	public void testMergeTag() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();
		Ref tag = git.tag().setAnnotated(true).setMessage("my tag 01")
				.setName("tag01").setObjectId(secondCommit).call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(tag).setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED
	}

