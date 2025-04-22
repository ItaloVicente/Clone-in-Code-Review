				if (member instanceof FileDescription) {
					IPath path = resource.getFullPath().append(((FileDescription) member).name);
					IFile fileHandle = resource.getWorkspace().getRoot().getFile(path);
					member.recordStateFromHistory(fileHandle, iterationMonitor);
				} else if (member instanceof FolderDescription) {
					IPath path = resource.getFullPath().append(((FolderDescription) member).name);
					IFolder folderHandle = resource.getWorkspace().getRoot().getFolder(path);
					member.recordStateFromHistory(folderHandle, iterationMonitor);
