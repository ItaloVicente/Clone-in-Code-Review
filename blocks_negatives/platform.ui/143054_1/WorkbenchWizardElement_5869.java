        return StructuredSelection.EMPTY;
    }

    /**
     * Create an the instance of the object described by the configuration
     * element. That is, create the instance of the class the isv supplied in
     * the extension point.
     * @return the new object
     * @throws CoreException
     */
    public Object createExecutableExtension() throws CoreException {
        return WorkbenchPlugin.createExtension(configurationElement,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    /**
     * Returns an object which is an instance of the given class associated
     * with this object. Returns <code>null</code> if no such object can be
     * found.
     */
