	public void testUserDefinedToolWithCancelledPrompt() throws Exception {
		String command = getEchoCommand();

		FileBasedConfig config = db.getConfig();
		String customToolName = "customTool";
		config.setString(CONFIG_DIFFTOOL_SECTION
				CONFIG_KEY_CMD

