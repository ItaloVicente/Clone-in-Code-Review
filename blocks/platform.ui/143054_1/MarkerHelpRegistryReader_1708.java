		if (element.getName().equals(TAG_HELP)) {
			readHelpElement(element);
			return true;
		}
		if (element.getName().equals(TAG_RESOLUTION_GENERATOR)) {
			readResolutionElement(element);
			return true;
		}
		if (element.getName().equals(TAG_ATTRIBUTE)) {
			readAttributeElement(element);
			return true;
		}
		return false;
	}

	private void readHelpElement(IConfigurationElement element) {
		String type = element.getAttribute(ATT_TYPE);
		boolean matchChildren = Boolean.parseBoolean(element.getAttribute(ATT_MATCH_CHILDREN));

