					IRunnableContext runnableContext = getContainer();
					if (runnableContext == null) {
						runnableContext = PlatformUI.getWorkbench()
								.getProgressService();
					}
					if (file != null) {
						left = new ResourceEditableRevision(rev, file,
								runnableContext);
					} else {
						left = new LocationEditableRevision(rev, location,
								runnableContext);
					}
					try {
						((EditableRevision) left).cacheContents(monitor);
					} catch (CoreException e) {
						throw new IOException(e.getMessage());
					}
