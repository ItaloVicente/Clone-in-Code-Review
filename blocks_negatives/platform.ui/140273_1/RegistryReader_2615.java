    /**
     * The constructor.
     */
    protected RegistryReader() {
    }

    /**
     * Logs the error in the workbench log using the provided
     * text and the information in the configuration element.
     */
    protected static void logError(IConfigurationElement element, String text) {
        IExtension extension = element.getDeclaringExtension();
        StringBuilder buf = new StringBuilder();
        buf
				.append("Plugin " + extension.getContributor().getName() + ", extension " //$NON-NLS-1$//$NON-NLS-2$
						+ extension.getExtensionPointUniqueIdentifier());
        if (id != null) {
        	buf.append(", id "); //$NON-NLS-1$
        	buf.append(id);
        }
        WorkbenchPlugin.log(buf.toString());
    }

    /**
     * Logs a very common registry error when a required attribute is missing.
     */
    protected static void logMissingAttribute(IConfigurationElement element,
            String attributeName) {
        logError(element,
    }

    /**
     * Logs a very common registry error when a required child is missing.
     */
    protected static void logMissingElement(IConfigurationElement element,
            String elementName) {
        logError(element,
    }

    /**
     * Logs a registry error when the configuration element is unknown.
     */
    protected static void logUnknownElement(IConfigurationElement element) {
        logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
    }

    /**
     * Apply a reproducable order to the list of extensions
     * provided, such that the order will not change as
     * extensions are added or removed.
     * @param extensions the extensions to order
     * @return ordered extensions
     */
    public static IExtension[] orderExtensions(IExtension[] extensions) {
