        if (o instanceof IResource) {
            return resourceFactory;
        }
        if (o instanceof IWorkspace) {
            return workspaceFactory;
        }
        return null;
    }

    /**
     * Returns an object which is an instance of IPersistableElement
     * associated with the given object. Returns <code>null</code> if
     * no such object can be found.
     */
