                else if (createLinks)
                    ((IFolder) currentFolder).createLink(createRelativePath(
                            path, currentFolder), 0, null);
                else
                    ((IFolder) currentFolder).create(false, true, null);
            }
        }

        return currentFolder;
    }

    /**
     * Creates the folders that appear in the specified resource path
     * assuming that the destinationContainer begins at the root. Do not create projects.
     *
     * @param path the relative path of the resource
     * @return the container resource coresponding to the given path
     * @exception CoreException if this method failed
     */
    private IContainer createFromRoot(IPath path) throws CoreException {

        int segmentCount = path.segmentCount();

        IContainer currentFolder = ((IWorkspaceRoot) destinationContainer)
                .getProject(path.segment(0));

        for (int i = 1; i < segmentCount; i++) {
            currentFolder = currentFolder.getFolder(new Path(path.segment(i)));
            if (!currentFolder.exists()) {
