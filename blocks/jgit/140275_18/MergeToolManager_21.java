	public Set<String> getAllToolNames() {
		String defaultName = getDefaultToolName(BooleanOption.DEFAULT_FALSE);
		if (defaultName == null) {
			defaultName = getFirstAvailableTool();
		}
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
