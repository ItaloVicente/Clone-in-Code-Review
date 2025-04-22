        }

        return selectedResources;
    }

    /**
     * Returns the resource object specified in the resource name entry field,
     * or <code>null</code> if such a resource does not exist in the workbench.
     *
     * @return the resource specified in the resource name entry field, or
     *   <code>null</code>
     */
    protected IResource getSourceResource() {
        IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();
        IPath testPath = getResourcePath();

        IStatus result = workspace.validatePath(testPath.toString(),
                IResource.ROOT | IResource.PROJECT | IResource.FOLDER
                        | IResource.FILE);

        if (result.isOK() && workspace.getRoot().exists(testPath)) {
			return workspace.getRoot().findMember(testPath);
