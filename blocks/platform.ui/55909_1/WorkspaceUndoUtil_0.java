		subMonitor.setTaskName(UndoMessages.AbstractResourcesOperation_DeleteResourcesProgress);
		for (int i = 0; i < resourcesToDelete.length; ++i) {
			if (subMonitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			IResource resource = resourcesToDelete[i];
			try {
				returnedResourceDescriptions[i] = delete(resource, subMonitor.newChild(1), uiInfo,
						forceOutOfSyncDelete, deleteContent);
			} catch (CoreException e) {
				if (resource.getType() == IResource.FILE) {
					IStatus[] children = e.getStatus().getChildren();
					if (children.length == 1 && children[0].getCode() == IResourceStatus.OUT_OF_SYNC_LOCAL) {
						int result = queryDeleteOutOfSync(resource, uiInfo);

						if (result == IDialogConstants.YES_ID) {
							delete(resource, subMonitor.newChild(1), uiInfo, true, deleteContent);
						} else if (result == IDialogConstants.YES_TO_ALL_ID) {
							forceOutOfSyncDelete = true;
							delete(resource, subMonitor.newChild(1), uiInfo, forceOutOfSyncDelete,
									deleteContent);
						} else if (result == IDialogConstants.CANCEL_ID) {
							throw new OperationCanceledException();
