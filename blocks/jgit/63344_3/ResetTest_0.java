	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git reset -h");
		assertFalse("Unexpected argument: " + result[0]
				result[0].endsWith(" [-- path ... ...]"));
	}

