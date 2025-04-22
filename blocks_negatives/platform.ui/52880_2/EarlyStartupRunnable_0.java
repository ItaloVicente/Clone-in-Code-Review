    /**
     * In 3.0 the class attribute is a mandatory element of the startup element.
     *
     * @return an executable extension for this startup element or null if an
     *         extension (or plugin) could not be found
     */
    private Object getExecutableExtension(IConfigurationElement element)
            throws CoreException {
		return WorkbenchPlugin.createExtension(element, IWorkbenchConstants.TAG_CLASS);
    }
