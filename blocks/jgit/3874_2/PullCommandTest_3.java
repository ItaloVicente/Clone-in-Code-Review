	@Test
	public void testPullWithContentMerger() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Target change in local").call();

		MockContentMerger contentMerger = new MockContentMerger(
				target.getRepository());
		res = target.pull().mergeWith(contentMerger).call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(res.getMergeResult().getMergeStatus()
		String result = "custom merge - Hello world:Target change:Source change\n";
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				.getRepositoryState());
	}

