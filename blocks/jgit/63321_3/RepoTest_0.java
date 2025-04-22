	@Test
	public void testMissingPath() throws Exception {
		assertEquals("fatal: Argument \"path\" is required"
				execute("git repo")[0]);
	}

	@Test
	public void testZombieHelpArgument() throws Exception {
		String[] output = execute("git repo -h");
		String all = Arrays.toString(output);
		assertTrue("Unexpected help output: " + all
				all.contains("jgit repo"));
		assertFalse("Unexpected help output: " + all
				all.contains("jgit repo VAL"));
	}

