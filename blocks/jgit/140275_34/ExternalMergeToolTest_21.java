	public void testUserDefinedToolWithPrompt() throws Exception {
		String customToolName = "customTool";
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		config.setString(CONFIG_MERGETOOL_SECTION
				CONFIG_KEY_CMD

