        return result;
    }

    /**
     *  Create the directories required for exporting the passed resource,
     *  based upon its container hierarchy
     *
     *  @param childResource org.eclipse.core.resources.IResource
     */
    protected void createLeadupDirectoriesFor(IResource childResource) {
        IPath resourcePath = childResource.getFullPath().removeLastSegments(1);

        for (int i = 0; i < resourcePath.segmentCount(); i++) {
            path = path.append(resourcePath.segment(i));
            exporter.createFolder(path);
        }
    }

    /**
     *	Recursively export the previously-specified resource
     */
    protected void exportAllResources() throws InterruptedException {
        if (resource.getType() == IResource.FILE) {
