	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git reset -h");
		assertTrue("Unexpected argument: " + result[1]
				result[1].endsWith("[-- path ... ...]"));
	}

	@Test
	public void testZombieArgument_Bug484951() throws Exception {
		String[] result = execute("git reset -h");
		assertFalse("Unexpected argument: " + result[0]
				result[0].contains("[VAL ...]"));
	}

