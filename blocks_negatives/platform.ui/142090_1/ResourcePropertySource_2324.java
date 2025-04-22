    /**
     * Returns whether the given resource is a linked resource bound
     * to a path variable.
     *
     * @param resource resource to test
     * @return boolean <code>true</code> the given resource is a linked
     * 	resource bound to a path variable. <code>false</code> the given
     * 	resource is either not a linked resource or it is not using a
     * 	path variable.
     */
    private boolean isPathVariable(IResource resource) {
        if (!resource.isLinked()) {
