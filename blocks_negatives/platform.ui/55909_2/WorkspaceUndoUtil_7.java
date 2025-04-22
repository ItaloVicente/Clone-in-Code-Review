		monitor.beginTask("", resourcesToRecreate.length); //$NON-NLS-1$
		monitor
				.setTaskName(UndoMessages.AbstractResourcesOperation_CreateResourcesProgress);
		try {
			for (int i = 0; i < resourcesToRecreate.length; ++i) {
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				try {
					resourcesToReturn[i] = resourcesToRecreate[i]
							.createResource(new SubProgressMonitor(monitor, 1));
				} catch (CoreException e) {
					exceptions.add(e);
				}
