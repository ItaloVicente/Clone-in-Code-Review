        IDEWorkbenchPlugin.getPluginWorkspace().run(monitor1 -> {
		    monitor1
		            .beginTask(
		                    IDEWorkbenchMessages.ContainerGenerator_progressMessage, 1000 * containerFullPath.segmentCount());
		    if (container != null) {
				return;
			}

		    IWorkspaceRoot root = getWorkspaceRoot();
		    container = (IContainer) root.findMember(containerFullPath);
		    if (container != null) {
				return;
			}

		    container = root;
		    for (int i = 0; i < containerFullPath.segmentCount(); i++) {
		        String currentSegment = containerFullPath.segment(i);
		        IResource resource = container.findMember(currentSegment);
		        if (resource != null) {
		        	if (resource.getType() == IResource.FILE) {
		        		String msg = NLS.bind(IDEWorkbenchMessages.ContainerGenerator_pathOccupied, resource.getFullPath().makeRelative());
		        		throw new CoreException(new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, 1, msg, null));
		        	}
		            container = (IContainer) resource;
		            monitor1.worked(1000);
		        } else {
		            if (i == 0) {
		                IProject projectHandle = createProjectHandle(root,
		                        currentSegment);
		                container = createProject(projectHandle,
		                        new SubProgressMonitor(monitor1, 1000));
		            } else {
		                IFolder folderHandle = createFolderHandle(
		                        container, currentSegment);
		                container = createFolder(folderHandle,
		                        new SubProgressMonitor(monitor1, 1000));
		            }
		        }
		    }
		}, null, IResource.NONE, monitor);
