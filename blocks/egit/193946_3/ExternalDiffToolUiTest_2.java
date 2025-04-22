
	private void configureEchoTool(String toolName) throws Exception {
		StoredConfig config = testRepository.getRepository().getConfig();
		config.clear();
		String subsection = null;
		config.setString(CONFIG_DIFF_SECTION, subsection, CONFIG_KEY_TOOL,
				toolName);

		String command = getEchoCommand(toolName);

		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_CMD,
				command);
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName,
				CONFIG_KEY_TRUST_EXIT_CODE, String.valueOf(Boolean.TRUE));
		config.setString(CONFIG_DIFFTOOL_SECTION, toolName, CONFIG_KEY_PROMPT,
				String.valueOf(false));
		config.save();
	}

	private String getEchoCommand(String toolName) {
		return "(echo " + toolName + "  local=\"$LOCAL\" remote=\"$REMOTE\" > "
				+ resultFile.toAbsolutePath().toString() + ")";
	}
