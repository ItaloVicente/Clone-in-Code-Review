        }
    }

    /**
     * Imports the specified file system container object into the workspace.
     * If the import fails, adds a status object to the list to be returned by
     * <code>getResult</code>.
     *
     * @param folderObject the file system container object to be imported
     * @param policy determines how the folder object and children are imported
     * @return the policy to use to import the folder's children
     * @throws CoreException
     */
