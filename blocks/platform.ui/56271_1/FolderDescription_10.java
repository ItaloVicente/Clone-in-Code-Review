		subMonitor.setWorkRemaining(200);
		if (location != null) {
			folderHandle.createLink(location, IResource.ALLOW_MISSING_LOCAL, subMonitor.newChild(100));
		} else {
			folderHandle.create(virtual ? IResource.VIRTUAL : 0, true, subMonitor.newChild(100));
		}
		if (subMonitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		createChildResources(folderHandle, subMonitor.newChild(100));
