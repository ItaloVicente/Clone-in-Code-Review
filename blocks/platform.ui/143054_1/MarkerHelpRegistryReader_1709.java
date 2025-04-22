		readElementChildren(element);
		String[] attributeNames = currentAttributeNames
				.toArray(new String[currentAttributeNames.size()]);
		String[] attributeValues = currentAttributeValues
				.toArray(new String[currentAttributeValues.size()]);

		MarkerQuery query = new MarkerQuery(type, attributeNames, matchChildren);
		MarkerQueryResult result = new MarkerQueryResult(attributeValues);
		markerHelpRegistry.addHelpQuery(query, result, element);
	}

	private void readResolutionElement(IConfigurationElement element) {
		String type = element.getAttribute(ATT_TYPE);

