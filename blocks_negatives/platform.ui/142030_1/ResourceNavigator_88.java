        return frameList;
    }

    /**
     * Returns the initial input for the viewer.
     * Tries to convert the page input to a resource, either directly or via IAdaptable.
     * If the resource is a container, it uses that.
     * If the resource is a file, it uses its parent folder.
     * If a resource could not be obtained, it uses the workspace root.
     *
     * @since 2.0
     */
