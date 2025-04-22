	public Set<String> getUserDefinedToolNames() {
		return userDefinedTools.keySet();
	}

	public Set<String> getPredefinedToolNames() {
		return predefinedTools.keySet();
	}

	public Set<String> getAllToolNames() {
		String defaultName = getDefaultToolName(false);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return Utils.createSortedToolSet(defaultName
				getPredefinedToolNames());
