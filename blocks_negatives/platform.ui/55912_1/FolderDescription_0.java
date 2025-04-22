		try {
			monitor.beginTask("", 200); //$NON-NLS-1$
			monitor.setTaskName(UndoMessages.FolderDescription_NewFolderProgress);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (filters != null) {
				for (int i = 0; i < filters.length; i++) {
					folderHandle.createFilter(filters[i].getType(), filters[i].getFileInfoMatcherDescription(), 0, new SubProgressMonitor(
							monitor, 100));
				}
			}
			if (location != null) {
				folderHandle.createLink(location,
						IResource.ALLOW_MISSING_LOCAL, new SubProgressMonitor(
								monitor, 100));
			} else {
				folderHandle.create(virtual ? IResource.VIRTUAL:0, true, new SubProgressMonitor(
						monitor, 100));
			}
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
