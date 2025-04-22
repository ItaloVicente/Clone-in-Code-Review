	@Test
	public void testDescribeCommitMatch() throws Exception {
		initialCommitAndTag();
		secondCommit();
		assertArrayEquals(new String[] { "v1.0-1-g56f6ceb"
				execute("git describe --match v1.*"));
	}

	@Test
	public void testDescribeCommitMatch2() throws Exception {
		initialCommitAndTag();
		secondCommit();
		git.tag().setName("v2.0").call();
		assertArrayEquals(new String[] { "v1.0-1-g56f6ceb"
				execute("git describe --match v1.*"));
	}

	@Test
	public void testDescribeCommitMultiMatch() throws Exception {
		initialCommitAndTag();
		secondCommit();
		git.tag().setName("v2.0.0").call();
		git.tag().setName("v2.1.1").call();
		assertArrayEquals("git yields v2.0.0"
				execute("git describe --match v2.0* --match v2.1.*"));
	}

	@Test
	public void testDescribeCommitNoMatch() throws Exception {
		initialCommitAndTag();
		writeTrashFile("greeting"
		secondCommit();
		try {
			execute("git describe --match 1.*");
			fail("git describe should not find any tag matching 1.*");
		} catch (Die e) {
			assertEquals("No names found
					e.getMessage());
		}
	}

