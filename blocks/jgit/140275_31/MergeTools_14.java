	public Set<String> getUserDefinedToolNames() {
		return userDefinedTools.keySet();
	}

	public Set<String> getPredefinedToolNames() {
		return predefinedTools.keySet();
	}

	public Set<String> getAllToolNames() {
		String defaultName = getDefaultToolName(BooleanOption.DEFAULT_FALSE);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
