	public void testCompare() throws ToolException {
		String toolName = "customTool";

		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_DIFFTOOL_SECTION
				command);
