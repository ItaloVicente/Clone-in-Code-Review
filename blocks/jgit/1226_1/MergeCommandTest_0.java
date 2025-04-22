	public void testContentMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("a"
		writeTrashFile("b"
		writeTrashFile("c\\c\\c"
		git.add().addFilepattern("a").addFilepattern("b").addFilepattern("c\\c\\c").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		RevCommit secondCommit = git.commit().setMessage("side").call();

		assertEquals("1\nb(side)\n3\n"
		checkoutBranch("refs/heads/master");
		assertEquals("1\nb\n3\n"

		writeTrashFile("a"
		writeTrashFile("c\\c\\c"
		git.add().addFilepattern("a").addFilepattern("c\\c\\c").call();
		git.commit().setMessage("main").call();

		MergeResult result = git.merge().include(secondCommit.getId()).setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertEquals("1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> c8c7f2b03d7edc0ad019f9223c63f29a933a8c6d\n3\n"
		assertEquals("1\nb(side)\n3\n"
		assertEquals("1\nc(main)\n3\n"

		assertEquals(1
		assertEquals(3
	}

