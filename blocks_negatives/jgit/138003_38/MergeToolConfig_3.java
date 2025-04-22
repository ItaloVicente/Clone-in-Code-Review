		toolName = rc.getString(ConfigConstants.CONFIG_MERGE_SECTION, null,
				ConfigConstants.CONFIG_KEY_TOOL);
		guiToolName = rc.getString(ConfigConstants.CONFIG_MERGE_SECTION, null,
				ConfigConstants.CONFIG_KEY_GUITOOL);
		prompt = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION,
				ConfigConstants.CONFIG_KEY_PROMPT, true);
		keepBackup = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION,
				ConfigConstants.CONFIG_KEY_KEEP_BACKUP, true);
		keepTemporaries = rc.getBoolean(
				ConfigConstants.CONFIG_MERGETOOL_SECTION,
				ConfigConstants.CONFIG_KEY_KEEP_TEMPORARIES, false);
		writeToTemp = rc.getBoolean(ConfigConstants.CONFIG_MERGETOOL_SECTION,
				ConfigConstants.CONFIG_KEY_WRITE_TO_TEMP, false);
