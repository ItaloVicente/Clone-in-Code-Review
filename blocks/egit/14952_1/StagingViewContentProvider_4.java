		List<StagingFolderEntry> compressedFoldersList = new ArrayList<StagingFolderEntry>();
		for (IPath folderPath : folderPaths) {
			IPath parent;
			for (parent = folderPath.removeLastSegments(1); parent
					.segmentCount() != 0; parent = parent.removeLastSegments(1)) {
				if (folderPaths.contains(parent)) {
					break;
