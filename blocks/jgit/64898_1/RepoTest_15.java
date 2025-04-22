	@Test
	public void testMissingPath() throws Exception {
		try {
			execute("git repo");
			fail("Must die");
		} catch (Die e) {
		}
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

