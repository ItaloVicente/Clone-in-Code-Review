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
