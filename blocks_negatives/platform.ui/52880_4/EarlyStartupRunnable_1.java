            IStatus status = new Status(IStatus.ERROR,
                    extension.getNamespace(), 0,
                    "startup class must implement org.eclipse.ui.IStartup", //$NON-NLS-1$
                    null);
            WorkbenchPlugin.log("Bad extension specification", status); //$NON-NLS-1$
        }
    }

    /**
     * In 3.0 the class attribute is a mandatory element of the startup element.
     * However, 2.1 plugins should still be able to run if the compatibility
     * bundle is loaded.
     * 
     * @return an executable extension for this startup element or null if an
     *         extension (or plugin) could not be found
     */
    private Object getExecutableExtension(IConfigurationElement element)
            throws CoreException {

        String classname = element.getAttribute(IWorkbenchConstants.TAG_CLASS);

        if (classname == null) {
        	return getPluginForCompatibility();
