	@Test
	public void testDescribeCommitMatchAbbrev() throws Exception {
		initialCommitAndTag();
		secondCommit();
		assertArrayEquals(new String[] { "v1.0-1-g56f6cebdf3f5"
				execute("git describe --abbrev 12 --match v1.*"));
	}

	@Test
	public void testDescribeCommitMatchAbbrevMin() throws Exception {
		initialCommitAndTag();
		secondCommit();
		assertArrayEquals(new String[] { "v1.0-1-g56f6"
				execute("git describe --abbrev -5 --match v1.*"));
	}

	@Test
	public void testDescribeCommitMatchAbbrevMax() throws Exception {
		initialCommitAndTag();
		secondCommit();
		assertArrayEquals(new String[] {
				"v1.0-1-g56f6cebdf3f5ceeecd803365abf0996fb1fa006d"
				execute("git describe --abbrev 50 --match v1.*"));
	}

