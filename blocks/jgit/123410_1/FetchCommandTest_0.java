
	@Test
	public void testFetchWithPruneShouldKeepOriginHead() throws Exception {
		commitFile("foo"
		Git cloned = Git.cloneRepository()
				.setDirectory(createTempDirectory("testCloneRepository"))
						+ git.getRepository().getWorkTree().getAbsolutePath())
				.call();
		assertNotNull(cloned);
		Repository clonedRepo = cloned.getRepository();
		addRepoToClose(clonedRepo);
		ObjectId originMasterId = clonedRepo
				.resolve("refs/remotes/origin/master");
		assertNotNull("Should have origin/master"
		assertNotEquals("origin/master should not be zero ID"
				ObjectId.zeroId()
		ObjectId originHeadId = clonedRepo.resolve("refs/remotes/origin/HEAD");
		if (originHeadId == null) {
			JGitTestUtil.write(
					new File(clonedRepo.getDirectory()
							"refs/remotes/origin/HEAD")
					"ref: refs/remotes/origin/master\n");
			originHeadId = clonedRepo.resolve("refs/remotes/origin/HEAD");
		}
		assertEquals("Should have origin/HEAD"
		FetchResult result = cloned.fetch().setRemote("origin")
				.setRemoveDeletedRefs(true).call();
		assertTrue("Fetch after clone should be up-to-date"
				result.getTrackingRefUpdates().isEmpty());
		assertEquals("origin/master should still exist"
				clonedRepo.resolve("refs/remotes/origin/master"));
		assertEquals("origin/HEAD should be unchanged"
				clonedRepo.resolve("refs/remotes/origin/HEAD"));
	}
