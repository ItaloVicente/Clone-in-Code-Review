        if (o instanceof IResource) {
            switch (((IResource) o).getType()) {
            case IResource.FILE:
                return fileAdapter;
            case IResource.FOLDER:
                return folderAdapter;
            case IResource.PROJECT:
                return projectAdapter;
            }
        }
        if (o instanceof IMarker) {
            return markerAdapter;
        }
        return null;
    }

    /**
     * Returns an object which is an instance of the given class
     * associated with the given object. Returns <code>null</code> if
     * no such object can be found.
     *
     * @param o the adaptable object being queried
     *   (usually an instance of <code>IAdaptable</code>)
     * @param adapterType the type of adapter to look up
     * @return a object castable to the given adapter type,
     *    or <code>null</code> if this adapter provider
     *    does not have an adapter of the given type for the
     *    given object
     */
    @Override
