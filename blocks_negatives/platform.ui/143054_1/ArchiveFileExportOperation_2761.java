    }

    /**
     *  Export the passed resource to the destination .zip
     *
     *  @param exportResource org.eclipse.core.resources.IResource
     *  @param leadupDepth the number of resource levels to be included in
     *                     the path including the resourse itself.
     */
    protected void exportResource(IResource exportResource, int leadupDepth)
            throws InterruptedException {
