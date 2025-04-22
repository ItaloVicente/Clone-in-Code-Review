		monitor.setTaskName(getOperationMessage());
		Iterator<? extends IResource> resourcesEnum = resources.iterator();
		try {
			while (resourcesEnum.hasNext()) {
				IResource resource = resourcesEnum.next();
				try {
					invokeOperation(resource, new SubProgressMonitor(monitor, 1000));
				} catch (CoreException e) {
					errors = recordError(errors, e);
				}
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
