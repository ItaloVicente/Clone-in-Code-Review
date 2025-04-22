    /**
     * Logs a very common registry error when a required attribute is missing.
     */
    protected void logMissingAttribute(IConfigurationElement element,
            String attributeName) {
        logError(element,
    }

    /**
     * Logs a very common registry error when a required child is missing.
     */
    protected void logMissingElement(IConfigurationElement element,
            String elementName) {
        logError(element,
    }

    /**
     * Logs a registry error when the configuration element is unknown.
     */
    protected void logUnknownElement(IConfigurationElement element) {
        logError(element, "Unknown extension tag found: " + element.getName());//$NON-NLS-1$
    }

    /**
     *	Apply a reproducable order to the list of extensions
     * provided, such that the order will not change as
     * extensions are added or removed.
     */
    protected IExtension[] orderExtensions(IExtension[] extensions) {
        IExtension[] sortedExtension = new IExtension[extensions.length];
        System.arraycopy(extensions, 0, sortedExtension, 0, extensions.length);
        Collections.sort(Arrays.asList(sortedExtension), comparer);
        return sortedExtension;
    }

    /**
     * Implement this method to read element's attributes.
     * If children should also be read, then implementor
     * is responsible for calling <code>readElementChildren</code>.
     * Implementor is also responsible for logging missing
     * attributes.
     *
     * @return true if element was recognized, false if not.
     */
    protected abstract boolean readElement(IConfigurationElement element);

    /**
     * Read the element's children. This is called by
     * the subclass' readElement method when it wants
     * to read the children of the element.
     */
    protected void readElementChildren(IConfigurationElement element) {
        readElements(element.getChildren());
    }

    /**
     * Read each element one at a time by calling the
     * subclass implementation of <code>readElement</code>.
     *
     * Logs an error if the element was not recognized.
     */
    protected void readElements(IConfigurationElement[] elements) {
        for (int i = 0; i < elements.length; i++) {
            if (!readElement(elements[i])) {
				logUnknownElement(elements[i]);
