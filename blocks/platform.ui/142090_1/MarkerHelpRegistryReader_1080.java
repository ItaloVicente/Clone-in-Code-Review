		MarkerQueryResult result = new MarkerQueryResult(attributeValues);
		markerHelpRegistry.addHelpQuery(query, result, element);
	}

	private void readResolutionElement(IConfigurationElement element) {
		String type = element.getAttribute(ATT_TYPE);

