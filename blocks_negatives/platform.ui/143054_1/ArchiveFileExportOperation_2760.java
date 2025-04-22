        return result;
    }

    /**
     *  Export the passed resource to the destination .zip. Export with
     * no path leadup
     *
     *  @param exportResource org.eclipse.core.resources.IResource
     */
    protected void exportResource(IResource exportResource)
            throws InterruptedException {
        exportResource(exportResource, 1);
    }

    /**
     * Creates and returns the string that should be used as the name of the entry in the archive.
     * @param exportResource the resource to export
     * @param leadupDepth the number of resource levels to be included in the path including the resourse itself.
     */
    private String createDestinationName(int leadupDepth, IResource exportResource) {
        IPath fullPath = exportResource.getFullPath();
        if (createLeadupStructure) {
        	return fullPath.makeRelative().toString();
        }
