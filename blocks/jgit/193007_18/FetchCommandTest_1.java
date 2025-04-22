	@Test
	public void testFetchHasRefLogForRemoteRef() throws Exception {
		ObjectId defaultBranchSha1 = remoteGit.commit().setMessage("initial commit").call().getId();

		git.fetch().setRemote("test")

		List<Ref> allFetchedRefs = git.getRepository().getRefDatabase().getRefs();
		assertEquals(allFetchedRefs.size()
		Ref remoteRef = allFetchedRefs.get(0);

		assertTrue(remoteRef.getName().startsWith(Constants.R_REMOTES));
		assertEquals(defaultBranchSha1
		assertNotNull(git.getRepository().getReflogReader(remoteRef.getName()).getLastEntry());
	}

