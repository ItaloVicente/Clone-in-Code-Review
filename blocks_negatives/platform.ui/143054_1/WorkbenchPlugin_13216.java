        if (operationSupport != null) {
        	operationSupport.dispose();
        	operationSupport = null;
        }

        DEBUG = false;

    }

    /**
     * Creates an extension.  If the extension plugin has not
     * been loaded a busy cursor will be activated during the duration of
     * the load.
     *
     * @param element the config element defining the extension
     * @param classAttribute the name of the attribute carrying the class
     * @return the extension object
     * @throws CoreException if the extension cannot be created
     */
    public static Object createExtension(final IConfigurationElement element,
            final String classAttribute) throws CoreException {
        try {
