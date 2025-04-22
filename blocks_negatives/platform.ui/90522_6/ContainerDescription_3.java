				if (members[i] instanceof FileDescription) {
					IPath path = resource.getFullPath().append(
							((FileDescription) members[i]).name);
					IFile fileHandle = resource.getWorkspace().getRoot().getFile(
							path);
					members[i].recordStateFromHistory(fileHandle, iterationMonitor);
				} else if (members[i] instanceof FolderDescription) {
					IPath path = resource.getFullPath().append(
							((FolderDescription) members[i]).name);
					IFolder folderHandle = resource.getWorkspace().getRoot()
							.getFolder(path);
					members[i].recordStateFromHistory(folderHandle, iterationMonitor);
