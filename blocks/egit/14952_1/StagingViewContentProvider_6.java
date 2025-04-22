
		compressedFolders = compressedFoldersList
				.toArray(new StagingFolderEntry[compressedFoldersList.size()]);
		Arrays.sort(compressedFolders, comparator);
		compressedRoots = compressedRootsList
				.toArray(new Object[compressedRootsList.size()]);
