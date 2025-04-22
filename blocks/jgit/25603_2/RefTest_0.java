	@Test
	public void testRemoteNames() throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"origin"
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"ab/c"
		config.save();
		assertEquals("master"
				db.shortenRemoteBranchName("refs/remotes/origin/master"));
		assertEquals("masta/r"
				db.shortenRemoteBranchName("refs/remotes/origin/masta/r"));
		assertEquals("xmaster"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmaster"));
		assertEquals("xmasta/r"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmasta/r"));
	}

