	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		String toolName = "customTool";

		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		DiffTools manager = new DiffTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;
		BooleanTriState trustExitCode = BooleanTriState.TRUE;

		manager.compare(local
				trustExitCode);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test(expected = ToolException.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_DIFFTOOL_SECTION
				command);

		DiffTools manager = new DiffTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;
		BooleanTriState trustExitCode = BooleanTriState.FALSE;

		manager.compare(local
				trustExitCode);

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

