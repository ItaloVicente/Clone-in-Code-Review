	@Test
	public void testPullConflictTheirs() throws Exception {
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

		res = target.pull().setStrategy(MergeStrategy.THEIRS).call();

		assertTrue(res.isSuccessful());
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				target.getRepository().getRepositoryState());
		assertTrue(target.status().call().isClean());
	}

	@Test
	public void testPullConflictXtheirs() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Multi-line change in remote").call();

		res = target.pull().call();
		assertTrue(res.isSuccessful());
		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Target change in local").call();

		res = target.pull().setContentMergeStrategy(ContentMergeStrategy.THEIRS)
				.call();

		assertTrue(res.isSuccessful());
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				target.getRepository().getRepositoryState());
		assertTrue(target.status().call().isClean());
	}

