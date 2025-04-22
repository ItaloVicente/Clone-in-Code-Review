	@Test
	public void testForcedFetch() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		remoteGit.commit().setMessage("commit2").call();
		git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master").call();

		remoteGit.commit().setAmend(true).setMessage("amended").call();
		FetchResult res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master").call();
		assertEquals(RefUpdate.Result.REJECTED
				res.getTrackingRefUpdate("refs/heads/master").getResult());
		res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/master")
				.setForceUpdate(true).call();
		assertEquals(RefUpdate.Result.FORCED
				res.getTrackingRefUpdate("refs/heads/master").getResult());
	}

