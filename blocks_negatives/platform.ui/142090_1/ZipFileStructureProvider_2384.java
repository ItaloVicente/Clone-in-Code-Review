            children.put(parent, childList);
        }

        childList.add(child);
    }

    /**
     * Creates a new container zip entry with the specified name, iff
     * it has not already been created.
     */
    protected void createContainer(IPath pathname) {
        if (directoryEntryCache.containsKey(pathname)) {
