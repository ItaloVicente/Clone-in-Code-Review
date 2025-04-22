	}

	protected abstract boolean readElement(IConfigurationElement element);

	protected void readElementChildren(IConfigurationElement element) {
		readElements(element.getChildren());
	}

	protected void readElements(IConfigurationElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			if (!readElement(elements[i])) {
