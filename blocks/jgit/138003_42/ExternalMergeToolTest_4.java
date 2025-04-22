	@Test(expected = ToolException.class)
	public void testUserToolWithError() throws Exception {
		String toolName = "customTool";

		int errorReturnCode = 1;
		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_TRUST_EXIT_CODE

		MergeTools manager = new MergeTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(db
				prompt

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

	@Test(expected = ToolException.class)
	public void testUserToolWithCommandNotFoundError() throws Exception {
		String toolName = "customTool";

		String command = "exit " + errorReturnCode;

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				command);

		MergeTools manager = new MergeTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(db
				prompt

		fail("Expected exception to be thrown due to external tool exiting with error code: "
				+ errorReturnCode);
	}

