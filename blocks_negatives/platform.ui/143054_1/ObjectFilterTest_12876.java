    /**
     * Add a filter element to the test.  This element must contain
     * a name value filter pair, as defined by the
     * <code>org.eclipse.ui.actionFilters</code> extension point.
     */
    public boolean addFilterElement(IConfigurationElement element) {
        if (name == null) {
