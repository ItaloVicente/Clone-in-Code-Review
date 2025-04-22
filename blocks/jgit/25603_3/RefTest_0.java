	@Test
	public void testRemoteNames() throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"origin"
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"ab/c"
		config.save();
		assertEquals("[ab/c
				new TreeSet<String>(db.getRemoteNames()).toString());

		assertEquals("master"
				db.shortenRemoteBranchName("refs/remotes/origin/master"));
		assertEquals("origin"

		assertEquals("masta/r"
				db.shortenRemoteBranchName("refs/remotes/origin/masta/r"));
		assertEquals("origin"

		assertEquals("xmaster"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmaster"));
		assertEquals("ab/c"

		assertEquals("xmasta/r"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmasta/r"));
		assertEquals("ab/c"

		assertNull(db.getRemoteName("refs/remotes/nosuchremote/x"));
		assertNull(db.shortenRemoteBranchName("refs/remotes/nosuchremote/x"));

		assertNull(db.getRemoteName("refs/remotes/abranch"));
		assertNull(db.shortenRemoteBranchName("refs/remotes/abranch"));

		assertNull(db.getRemoteName("refs/heads/abranch"));
		assertNull(db.shortenRemoteBranchName("refs/heads/abranch"));
	}

