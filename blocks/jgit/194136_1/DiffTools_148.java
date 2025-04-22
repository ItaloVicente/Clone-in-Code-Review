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

	public Set<String> getPredefinedAvailableTools() {
		Map<String
		Set<String> availableTools = new LinkedHashSet<>();
		for (Entry<String
			if (elem.getValue().isAvailable()) {
				availableTools.add(elem.getKey());
			}
		}
		return availableTools;
	}

