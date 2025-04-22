	@Test
	public void testConflicts() throws Exception {
		RevCommit initial = git.commit().setMessage("initial").call();
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		RevCommit master = git.commit().setMessage("master").call();
		git.checkout().setName("refs/heads/side")
				.setCreateBranch(true).setStartPoint(initial).call();
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		RevCommit side = git.commit().setMessage("side").call();
		assertFalse(git.merge().include("master"
				.getMergeStatus()
				.isSuccessful());
		assertEquals(read(FILE)
				"<<<<<<< HEAD\nside\n=======\nmaster\n>>>>>>> master\n");
		writeTrashFile(FILE

		TreeWalk treeWalk = createTreeWalk(side);
		int count = 0;
		while (treeWalk.next())
			count++;
		assertEquals(2
	}

