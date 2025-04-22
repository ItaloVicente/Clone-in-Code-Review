	protected RegistryReader() {
	}

	protected static void logError(IConfigurationElement element, String text) {
		IExtension extension = element.getDeclaringExtension();
		StringBuilder buf = new StringBuilder();
		buf.append("Plugin " + extension.getContributor().getName() + ", extension " //$NON-NLS-1$//$NON-NLS-2$
				+ extension.getExtensionPointUniqueIdentifier());
		String id = element.getAttribute("id"); //$NON-NLS-1$
		if (id != null) {
			buf.append(", id "); //$NON-NLS-1$
			buf.append(id);
		}
		buf.append(": " + text);//$NON-NLS-1$
		WorkbenchPlugin.log(buf.toString());
	}

	protected static void logMissingAttribute(IConfigurationElement element, String attributeName) {
		logError(element, "Required attribute '" + attributeName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected static void logMissingElement(IConfigurationElement element, String elementName) {
		logError(element, "Required sub element '" + elementName + "' not defined");//$NON-NLS-2$//$NON-NLS-1$
	}

	protected static void logUnknownElement(IConfigurationElement element) {
		logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
	}

	public static IExtension[] orderExtensions(IExtension[] extensions) {
