        }

        return currentFolder;
    }

    /**
     * Deletes the given resource. If the resource fails to be deleted, adds a
     * status object to the list to be returned by <code>getResult</code>.
     *
     * @param resource the resource
     */
    void deleteResource(IResource resource) {
        try {
            resource.delete(IResource.KEEP_HISTORY, null);
        } catch (CoreException e) {
            errorTable.add(e.getStatus());
        }
    }

    @Override
