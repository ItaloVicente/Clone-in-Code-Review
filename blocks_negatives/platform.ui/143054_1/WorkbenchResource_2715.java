        IResource resource = getResource(o);
        return resource == null ? null : resource.getName();
    }

    /**
     * Returns the parent of the given object.  Returns null if the
     * parent is not available.
     */
    @Override
