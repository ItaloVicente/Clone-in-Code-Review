	private void configureEchoTool(String toolName) {
		StoredConfig config = db.getConfig();
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION
				toolName);

		String command = getEchoCommand();

		config.setString(CONFIG_DIFFTOOL_SECTION
				command);
		config.setString(CONFIG_DIFFTOOL_SECTION
				String.valueOf(false));
	}

