    /**
     * Check an individual resource to see if it passed the read only query. If it is a file
     * just add it, otherwise it is a container and the children need to be checked too.
     * Return true if all items are selected and false if any are skipped.
     */
    private boolean checkAcceptedResource(IResource resourceToCheck,
            List selectedChildren) throws CoreException {
