    /**
     * Returns whether any of the given resources are linked resources.
     *
     * @param resources resource to check for linked type. may be null
     * @return true=one or more resources are linked. false=none of the
     * 	resources are linked
     */
    private boolean isLinked(IResource[] resources) {
        for (IResource resource : resources) {
            if (resource.isLinked()) {
