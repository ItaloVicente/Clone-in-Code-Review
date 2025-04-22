    /**
     * {@inheritDoc}
     * The <code>WorkbenchPart</code> implementation of this
     * <code>IExecutableExtension</code> records the configuration element in
     * and internal state variable (accessible via <code>getConfigElement</code>).
     * It also loads the title image, if one is specified in the configuration element.
     * Subclasses may extend.
     *
     * Should not be called by clients. It is called by the core plugin when creating
     * this executable extension.
     */
    @Override
	public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {

        configElement = cfig;

        title = partName;

        if (strIcon == null) {
