		Map<String, Object> commandParameters = new HashMap<>();
		commandParameters.put(LTK_RENAME_COMMAND_NEWNAME_KEY, newName);
		return runCommand(LTK_RENAME_ID, structuredSelection, commandParameters);
	}

	public static boolean openRenameWizard(IStructuredSelection structuredSelection) {
		return runCommand(LTK_RENAME_ID, structuredSelection, Collections.emptyMap());
