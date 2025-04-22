	public static boolean isCompositeRename(IStructuredSelection structuredSelection) {
		Map<String, Object> commandParameters = new HashMap<>();
		commandParameters.put(LTK_CHECK_COMPOSITE_RENAME_PARAMETER_KEY, true);
		return runCommand(LTK_RENAME_ID, structuredSelection, commandParameters);
	}

	private static boolean runCommand(String commandId, IStructuredSelection selection,
			Map<String, Object> commandParameters) {

