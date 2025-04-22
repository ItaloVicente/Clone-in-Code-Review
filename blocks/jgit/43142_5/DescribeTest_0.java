
	@Test
	public void testDescribeTagLong() throws Exception {
		initialCommitAndTag();
		assertArrayEquals(new String[] { "v1.0-0-g6fd41be"
				execute("git describe --long HEAD"));
	}
