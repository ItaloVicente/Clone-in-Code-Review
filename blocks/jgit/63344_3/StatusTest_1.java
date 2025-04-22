	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git status -h");
		assertFalse("Unexpected argument: " + result[0]
				result[0].endsWith(" [-- path ... ...]"));
	}

