	@Test
	public void testPullFastUnadvertisefRef() throws Exception {
		PullResult res = target.pull()
				.setRemoteBranchName(source.getRepository()
						.findRef(source.getRepository().getFullBranch())
						.getObjectId().getName().toString())
				.call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		res = target.pull().call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.FAST_FORWARD);
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				target.getRepository().getRepositoryState());

		res = target.pull().call();
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.ALREADY_UP_TO_DATE);
	}

