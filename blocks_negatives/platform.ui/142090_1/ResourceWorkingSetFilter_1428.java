    /**
     * Returns if the given resource is enclosed by a working set element.
     * The IContainmentAdapter of each working set element is used for the
     * containment test. If there is no IContainmentAdapter for a working
     * set element, a simple resource based test is used.
     *
     * @param element resource to test for enclosure by a working set
     * 	element
     * @return true if element is enclosed by a working set element and
     * 	false otherwise.
     */
    private boolean isEnclosed(IResource element) {
        IPath elementPath = element.getFullPath();
        IAdaptable[] workingSetElements = cachedWorkingSet;
