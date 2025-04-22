	@Test
	public void testPullFastForwardWithBranchInSource() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.UP_TO_DATE

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		RevCommit initialCommit = source.commit()
				.setMessage("Some change in remote").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		RevCommit sideCommit = source.commit()
				.setMessage("Some change in remote").call();

		checkoutBranch("refs/heads/master");
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		MergeResult result = source.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

	}

