     * Get the message used to denote an empty destination.
     */
    protected String destinationEmptyMessage() {
        return DataTransferMessages.FileExport_destinationEmpty;
    }

    /**
     * Returns the name of a container with a location that encompasses targetDirectory.
     * Returns null if there is no conflict.
     *
     * @param targetDirectory the path of the directory to check.
     * @return the conflicting container name or <code>null</code>
     */
    protected String getConflictingContainerNameFor(String targetDirectory) {

        IPath rootPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
        IPath testPath = new Path(targetDirectory);
        if(testPath.equals(rootPath))
        	return rootPath.lastSegment();

        if(testPath.matchingFirstSegments(rootPath) == rootPath.segmentCount()){
        	String firstSegment = testPath.removeFirstSegments(rootPath.segmentCount()).segment(0);
        	if(!Character.isLetterOrDigit(firstSegment.charAt(0)))
        		return firstSegment;
        }

        return null;

    }

    /**
