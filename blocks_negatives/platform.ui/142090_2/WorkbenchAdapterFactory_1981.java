        if (o instanceof IResource) {
            return new ResourceFactory((IResource) o);
        }
        if (o instanceof IWorkspace) {
            return workspaceFactory;
        }
        return null;
    }

    /**
     * Returns an object which is an instance of IWorkbenchAdapter
     * associated with the given object. Returns <code>null</code> if
     * no such object can be found.
     */
