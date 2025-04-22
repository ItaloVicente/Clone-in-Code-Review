					if (i == 0) {
						IProject projectHandle = createProjectHandle(root, currentSegment);
						container = createProject(projectHandle, subMonitor.split(1));
					} else {
						IFolder folderHandle = createFolderHandle(container, currentSegment);
						container = createFolder(folderHandle, subMonitor.split(1));
					}
