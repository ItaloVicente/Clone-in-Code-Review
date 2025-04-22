	public void testPullLocalConflict() throws Exception {
		target.branchCreate().setName("basedOnMaster").setStartPoint(
				"refs/heads/master").setUpstreamMode(SetupUpstreamMode.TRACK)
				.call();
		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/basedOnMaster");
		PullResult res = target.pull().call();
		assertNull(res.getFetchResult());
		assertTrue(res.getMergeResult().getMergeStatus().equals(
				MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/master");
		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Source change in master").call();

		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/basedOnMaster");
		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Source change in based on master").call();

		res = target.pull().call();

		String sourceChangeString = "Master change\n>>>>>>> branch 'refs/heads/master' of local repository";

		assertNull(res.getFetchResult());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.CONFLICTING);
		String result = "<<<<<<< HEAD\nSlave change\n=======\n"
				+ sourceChangeString + "\n";
		assertFileContentsEqual(targetFile
	}

