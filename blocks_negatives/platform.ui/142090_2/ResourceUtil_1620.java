    /**
     * Tries to extra a single file from the given resource mapping.
     * Returns the file if the mapping maps to a single file, or <code>null</code>
     * if it maps to zero or multiple files.
     *
     * @param mapping the resource mapping
     * @return the file, or <code>null</code>
     */
    private static IFile getFileFromResourceMapping(ResourceMapping mapping) {
    	IResource resource = getResourceFromResourceMapping(mapping);
    	if (resource instanceof IFile) {
    		return (IFile) resource;
    	}
    	return null;
    }

    /**
     * Tries to extra a single resource from the given resource mapping.
     * Returns the resource if the mapping maps to a single resource, or <code>null</code>
     * if it maps to zero or multiple resources.
     *
     * @param mapping the resource mapping
     * @return the resource, or <code>null</code>
     */
    private static IResource getResourceFromResourceMapping(ResourceMapping mapping) {
