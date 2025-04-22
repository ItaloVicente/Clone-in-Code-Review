
	@Test
	public void testHelpArgumentBeforeUnknown() throws Exception {
		String[] output = execute("git describe -h -XYZ");
		String all = Arrays.toString(output);
		assertTrue("Unexpected help output: " + all
				all.contains("jgit describe"));
		assertFalse("Unexpected help output: " + all
	}

	@Test
	public void testHelpArgumentAfterUnknown() throws Exception {
		String[] output = executeUnchecked("git describe -XYZ -h");
		String all = Arrays.toString(output);
		assertTrue("Unexpected help output: " + all
				all.contains("jgit describe"));
		assertTrue("Unexpected help output: " + all
	}
