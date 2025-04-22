				if (i == 0) {
					firstCreatedParent = new ProjectDescription(root
							.getProject(currentSegment));
					currentContainerDescription = firstCreatedParent;
				} else {
					IFolder folderHandle = currentContainer.getFolder(new Path(
							currentSegment));
					ContainerDescription currentFolder;
					currentFolder = new FolderDescription(folderHandle, usingVirtualFolder);
					currentContainer = folderHandle;
					if (currentContainerDescription != null) {
						currentContainerDescription.addMember(currentFolder);
					}
					currentContainerDescription = currentFolder;
					if (firstCreatedParent == null) {
						firstCreatedParent = currentFolder;
					}
