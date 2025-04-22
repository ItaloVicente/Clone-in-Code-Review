    /**
     * Returns if the given resource is enclosed by a working set element.
     * A resource is enclosed if it is either a parent of a working set
     * element, a child of a working set element or a working set element
     * itself.
     * Simple path comparison is used. This is only guaranteed to return
     * correct results for resource working set elements.
     *
     * @param element resource to test for enclosure by a working set
     * 	element
     * @param elementPath full, absolute path of the element to test
     * @return true if element is enclosed by a working set element and
     * 	false otherwise.
     */
    private boolean isEnclosedResource(IResource element, IPath elementPath,
            IAdaptable workingSetElement) {
        IResource workingSetResource = null;
