			IPath location = workingDirectory.append(folderPath);
			if (parent.segmentCount() == 0) {
				StagingFolderEntry folderEntry = new StagingFolderEntry(
						location, folderPath);
				compressedRootsList.add(folderEntry);
			} else {
				IPath nodePath = folderPath.makeRelativeTo(parent);
				StagingFolderEntry folderEntry = new StagingFolderEntry(
						location, nodePath);
				compressedFoldersList.add(folderEntry);
			}
