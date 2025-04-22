	@Test
	public void testPullMerge() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt");
		RevCommit sourceCommit = source.commit()
				.setMessage("Source change in remote").call();

		File targetFile2 = new File(dbTarget.getWorkTree()
		writeToFile(targetFile2
		target.add().addFilepattern("OtherFile.txt").call();
		RevCommit targetCommit = target.commit()
				.setMessage("Unconflicting change in local").call();

		res = target.pull().call();

		MergeResult mergeResult = res.getMergeResult();
		ObjectId[] mergedCommits = mergeResult.getMergedCommits();
		assertEquals(targetCommit.getId()
		assertEquals(sourceCommit.getId()
		RevCommit mergeCommit = new RevWalk(dbTarget).parseCommit(mergeResult
				.getNewHead());
		String message = "Merge branch 'master' of " + db.getWorkTree();
		assertEquals(message
	}

