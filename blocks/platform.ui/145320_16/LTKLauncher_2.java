		Map<String, Object> commandParameters;
		if (newName != null) {
			commandParameters = Collections.singletonMap(LTK_RENAME_COMMAND_NEWNAME_PARAMETER_KEY, newName);
		} else {
			commandParameters = Collections.emptyMap();
		}
		return runCommand(LTK_RENAME_ID, structuredSelection, commandParameters);
