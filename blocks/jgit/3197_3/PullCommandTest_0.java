	@Test
	public void testPullDeletedChange() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		sourceFile.delete();
		source.rm().addFilepattern(sourceFile.getName()).call();
		source.commit().setMessage("Source deleted in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern(targetFile.getName()).call();
		target.commit().setMessage("Target changed in local").call();

		res = target.pull().call();


		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(MergeStatus.CONFLICTING
				.getMergeStatus());
		assertEquals(RepositoryState.MERGING
				.getRepositoryState());
	}

	@Test
	public void testPullChangedDeletion() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern(sourceFile.getName()).call();
		source.commit().setMessage("Source changed in remote").call();

		targetFile.delete();
		target.rm().addFilepattern(targetFile.getName()).call();
		target.commit().setMessage("Target deleted in local").call();

		res = target.pull().call();


		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(MergeStatus.CONFLICTING
				.getMergeStatus());
		assertEquals(RepositoryState.MERGING
				.getRepositoryState());
	}

