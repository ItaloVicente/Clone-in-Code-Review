	@Test
	public void testMissingPath() throws Exception {
		assertEquals("fatal: Argument \"path\" is required"
				execute("git repo")[0]);
	}

