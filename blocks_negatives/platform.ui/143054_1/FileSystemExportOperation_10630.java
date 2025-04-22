            try {
                exportChildren(((IContainer) resource).members(), path);
            } catch (CoreException e) {
                errorTable.add(e.getStatus());
            }
        }
    }

    /**
     *	Export all of the resources contained in the passed collection
     *
     *	@param children java.util.Enumeration
     *	@param currentPath IPath
     */
    protected void exportChildren(IResource[] children, IPath currentPath)
            throws InterruptedException {
