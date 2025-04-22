	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git status -h");
		assertTrue("Unexpected argument: " + result[1]
				result[1].endsWith("[-- path ... ...]"));
	}

