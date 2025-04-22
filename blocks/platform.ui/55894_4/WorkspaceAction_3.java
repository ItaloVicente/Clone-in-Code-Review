		subMonitor.setTaskName(getOperationMessage());
		for (IResource resource : resources) {
			try {
				invokeOperation(resource, subMonitor.newChild(1));
			} catch (CoreException e) {
				errors = recordError(errors, e);
			}
			if (subMonitor.isCanceled()) {
				throw new OperationCanceledException();
