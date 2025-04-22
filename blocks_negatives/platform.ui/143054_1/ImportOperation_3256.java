        IPath sourcePath = new Path(provider.getFullPath(source));
        IPath destContainerPath = pathname.removeLastSegments(1);
        IPath relativePath = destContainerPath.removeFirstSegments(
                sourcePath.segmentCount()).setDevice(null);
        return createContainersFor(relativePath);

    }

    /**
     * Returns the resource either casted to or adapted to an IFile.
     *
     * @param resource resource to cast/adapt
     * @return the resource either casted to or adapted to an IFile.
     * 	<code>null</code> if the resource does not adapt to IFile
     */
    IFile getFile(IResource resource) {
