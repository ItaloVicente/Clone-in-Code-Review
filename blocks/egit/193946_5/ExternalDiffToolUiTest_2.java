
	private void configureEchoTools(String defaultToolName,
			String... extraToolNames) throws Exception {
		configureTools(this::configureDefaultTool,
				this::configureEchoToolSubsection, defaultToolName,
				extraToolNames);
	}

	private void configureDefaultTool(String defaultToolName,
			StoredConfig config) {
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION, subsection, CONFIG_KEY_TOOL,
				defaultToolName);
	}

	private void configureEchoToolSubsection(String toolName,
			StoredConfig config) {
		String command = getEchoCommand(toolName);

		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_CMD,
				command);
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName,
				CONFIG_KEY_TRUST_EXIT_CODE, String.valueOf(Boolean.TRUE));
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_PROMPT,
				String.valueOf(false));
	}

	private String getEchoCommand(String toolName) {
		return "(echo " + toolName + "  local=\"$LOCAL\" remote=\"$REMOTE\" > "
				+ resultFile.toAbsolutePath().toString() + ")";
	}
