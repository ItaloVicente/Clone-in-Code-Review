    /**
     * This executable extension requires parameters to be explicitly declared
     * via the second method described in the <code>IExecutableExtension</code>
     * documentation.  This class expects that there will be two parameters,
     * <code>color1</code> and <code>color2</code>, that describe the two colors
     * to be blended.  These values may either be RGB triples or SWT constants.
     *
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
     */
    @Override
	public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
