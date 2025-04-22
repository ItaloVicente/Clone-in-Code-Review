	public Set<String> getPredefinedToolNames() {
		return predefinedTools.keySet();
	}

	public Set<String> getAllToolNames() {
		String defaultName = getDefaultToolName(
				BooleanOption.NOT_DEFINED_FALSE);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return Utils.createSortedToolSet(defaultName
				getPredefinedToolNames());
