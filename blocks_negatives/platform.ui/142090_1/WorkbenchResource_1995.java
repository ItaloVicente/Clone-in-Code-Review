        IResource resource = getResource(o);
        return resource == null ? null : resource.getParent();
    }

    /**
     * Returns the resource corresponding to this object,
     * or null if there is none.
     */
    protected IResource getResource(Object o) {
