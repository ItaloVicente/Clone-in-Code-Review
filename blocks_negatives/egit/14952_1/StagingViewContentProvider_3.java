		if (compressedRoots == null) {
			List<Object> compressedRootList = new ArrayList<Object>();
			compressedFolders = getCompressedFolders();
			for (StagingFolderEntry compressedFolder : compressedFolders)
				compressedRootList.add(compressedFolder);
			for (StagingEntry rootFile : rootFiles)
				compressedRootList.add(rootFile);
			compressedRoots = new Object[compressedRootList.size()];
			compressedRootList.toArray(compressedRoots);
		}
