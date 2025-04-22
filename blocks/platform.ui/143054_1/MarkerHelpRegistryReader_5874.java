		readElementChildren(element);
		String[] attributeNames = currentAttributeNames
				.toArray(new String[currentAttributeNames.size()]);
		String[] attributeValues = currentAttributeValues
				.toArray(new String[currentAttributeValues.size()]);

		MarkerQuery query = new MarkerQuery(type, attributeNames);
		MarkerQueryResult result = new MarkerQueryResult(attributeValues);
		markerHelpRegistry.addResolutionQuery(query, result, element);
	}

	private void readAttributeElement(IConfigurationElement element) {
		String name = element.getAttribute(ATT_NAME);
		String value = element.getAttribute(ATT_VALUE);
		if (name != null && value != null) {
			currentAttributeNames.add(name);
			currentAttributeValues.add(value);
		}
	}
