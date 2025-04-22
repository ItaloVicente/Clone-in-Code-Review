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
		return ExternalToolUtils.createSortedToolSet(defaultName
				getUserDefinedToolNames()
	}

	public Optional<String> getExternalToolFromAttributes(final String path)
			throws ToolException {
		return ExternalToolUtils.getExternalToolFromAttributes(repo
				ExternalToolUtils.KEY_DIFF_TOOL);
	}

