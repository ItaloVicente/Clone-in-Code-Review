	@Test
	public void testPullWithUntrackedStash() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(new File(dbTarget.getWorkTree()
				"untracked");

		RevCommit revCommit = target.stashCreate()
				.setIndexMessage("message here").setIncludeUntracked(true)
				.call();

		assertNotNull(revCommit);
		String stashName = revCommit.getName();

		res = target.pull().call();

		target.stashApply().setStashRef(stashName).call();
	}

