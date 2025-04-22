	public Set<String> getPredefinedAvailableTools() {
		Map<String
		Set<String> availableTools = new LinkedHashSet<>();
		for (Entry<String
			if (elem.getValue().isAvailable()) {
				availableTools.add(elem.getKey());
			}
		}
		return availableTools;
