	protected String destinationEmptyMessage() {
		return DataTransferMessages.FileExport_destinationEmpty;
	}

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

