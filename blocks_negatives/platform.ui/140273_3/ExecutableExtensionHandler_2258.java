    /**
     * Initializes this handler with data provided from XML. By default, an
     * <code>ExecutableExtensionHandler</code> will do nothing with this
     * information. Subclasses should override if they expect parameters from
     * XML.
     *
     * @param config
     *            the configuration element used to trigger this execution. It
     *            can be queried by the executable extension for specific
     *            configuration properties
     * @param propertyName
     *            the name of an attribute of the configuration element used on
     *            the <code>createExecutableExtension(String)</code> call.
     *            This argument can be used in the cases where a single
     *            configuration element is used to define multiple executable
     *            extensions.
     * @param data
     *            adapter data in the form of a <code>String</code>, a
     *            <code>Hashtable</code>, or <code>null</code>.
     * @throws CoreException
     *             if error(s) detected during initialization processing
     *
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
     *      java.lang.String, java.lang.Object)
     */
    @Override
	public void setInitializationData(final IConfigurationElement config,
            final String propertyName, final Object data) throws CoreException {
    }
