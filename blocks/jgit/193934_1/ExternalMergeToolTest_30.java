		String toolName = "undefined";
		invokeMerge(toolName);
		fail("Expected exception to be thrown due to not defined external merge tool");
	}

	@Test
	public void testDefaultToolExecutionWithPrompt() throws Exception {
		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString("merge"

		String command = getEchoCommand();

		config.setString("mergetool"

