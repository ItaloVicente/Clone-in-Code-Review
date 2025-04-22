				try {
					while (resourcesEnum.hasNext()) {
						try {
							IResource resource = resourcesEnum.next();
							refreshResource(resource, new SubProgressMonitor(monitor, 1000));
						} catch (CoreException e) {
							errors = recordError(errors, e);
						}
						if (monitor.isCanceled()) {
							throw new OperationCanceledException();
						}
