	@Test
	public void testFetchForceUpdate() throws Exception {
		String[] result = execute(
				"git fetch test refs/heads/master:refs/remotes/origin/master");
		assertEquals(" * [new branch]      master     -> origin/master"
				result[1]);
		assertEquals(" * [new tag]         tag        -> tag"
		remoteGit.commit().setAmend(true).setMessage("amended").call();
		result = execute(
				"git fetch -f test refs/heads/master:refs/remotes/origin/master");
		assertEquals(""
	}

