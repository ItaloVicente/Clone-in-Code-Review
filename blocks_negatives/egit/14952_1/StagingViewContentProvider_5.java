			compressedFolders = new StagingFolderEntry[compressedFolderList
					.size()];
			compressedFolderList.toArray(compressedFolders);
			Arrays.sort(compressedFolders, comparator);
			rootFiles = new StagingEntry[rootFileList.size()];
			rootFileList.toArray(rootFiles);
