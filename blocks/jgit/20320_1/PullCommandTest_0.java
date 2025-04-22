	@Test
	public void testPullMergeProgrammaticConfiguration() throws Exception {
		source.checkout().setCreateBranch(true).setName("other").call();
		sourceFile = new File(db.getWorkTree()
		writeToFile(sourceFile
		source.add().addFilepattern("file2.txt").call();
		RevCommit sourceCommit = source.commit()
				.setMessage("source commit on branch other").call();

		File targetFile2 = new File(dbTarget.getWorkTree()
		writeToFile(targetFile2
		target.add().addFilepattern("OtherFile.txt").call();
		RevCommit targetCommit = target.commit()
				.setMessage("Unconflicting change in local").call();

		PullResult res = target.pull().setRemote("origin"
				.setRebase(false).call();

		MergeResult mergeResult = res.getMergeResult();
		ObjectId[] mergedCommits = mergeResult.getMergedCommits();
		assertEquals(targetCommit.getId()
		assertEquals(sourceCommit.getId()
		RevCommit mergeCommit = new RevWalk(dbTarget).parseCommit(mergeResult
				.getNewHead());
		String message = "Merge branch 'other' of "
				+ db.getWorkTree().getAbsolutePath();
		assertEquals(message
	}

