	public Optional<String> getExternalToolFromAttributes(final String path)
			throws ToolException {
		return ExternalToolUtils.getExternalToolFromAttributes(db
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

