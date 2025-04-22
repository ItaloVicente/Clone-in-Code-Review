		String toolName = "customTool";

		FileBasedConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_MERGE_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_MERGETOOL_SECTION
				command);
