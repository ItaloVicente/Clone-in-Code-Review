	protected void logMissingAttribute(IConfigurationElement element,
			String attributeName) {
		logError(element,
				"Required attribute '" + attributeName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected void logMissingElement(IConfigurationElement element,
			String elementName) {
		logError(element,
				"Required sub element '" + elementName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected void logUnknownElement(IConfigurationElement element) {
		logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
	}

	protected IExtension[] orderExtensions(IExtension[] extensions) {
		IExtension[] sortedExtension = new IExtension[extensions.length];
		System.arraycopy(extensions, 0, sortedExtension, 0, extensions.length);
		Collections.sort(Arrays.asList(sortedExtension), comparer);
		return sortedExtension;
	}

	protected abstract boolean readElement(IConfigurationElement element);

	protected void readElementChildren(IConfigurationElement element) {
		readElements(element.getChildren());
	}

	protected void readElements(IConfigurationElement[] elements) {
		for (IConfigurationElement element : elements) {
			if (!readElement(element)) {
				logUnknownElement(element);
