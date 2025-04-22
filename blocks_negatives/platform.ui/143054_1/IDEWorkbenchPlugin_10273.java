    /**
     * Creates an extension.  If the extension plugin has not
     * been loaded a busy cursor will be activated during the duration of
     * the load.
     *
     * @param element the config element defining the extension
     * @param classAttribute the name of the attribute carrying the class
     * @return Object the extension object
     * @throws CoreException
     */
    public static Object createExtension(final IConfigurationElement element,
