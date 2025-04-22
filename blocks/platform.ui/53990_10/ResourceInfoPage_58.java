		Job.create(IDEWorkbenchMessages.ResourceInfo_recursiveChangesJobName, monitor -> {
			try {
				List/*<IResource>*/ toVisit = getResourcesToVisit(resource);

				monitor.beginTask(
						IDEWorkbenchMessages.ResourceInfo_recursiveChangesJobName,
						toVisit.size());

				for (Iterator/*<IResource>*/ it = toVisit.iterator(); it.hasNext();) {
					if (monitor.isCanceled())
						throw new OperationCanceledException();
					IResource childResource = (IResource) it.next();
					monitor.subTask(NLS
							.bind(IDEWorkbenchMessages.ResourceInfo_recursiveChangesSubTaskName,
									childResource.getFullPath()));
					for (int i = 0; i < changes.size(); i++) {
						((IResourceChange) changes.get(i))
								.performChange(childResource);
