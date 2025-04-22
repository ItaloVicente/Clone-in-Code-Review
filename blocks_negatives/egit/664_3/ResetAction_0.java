
			try {
				getTargetPart().getSite().getWorkbenchWindow().run(true, false,
						new IRunnableWithProgress() {
					public void run(final IProgressMonitor monitor)
					throws InvocationTargetException {
						try {
							new ResetOperation(repository, refName, type).execute(monitor);
							GitLightweightDecorator.refresh();
						} catch (CoreException e) {
							if (GitTraceLocation.UI.isActive())
								GitTraceLocation.getTrace().trace(GitTraceLocation.UI.getLocation(), e.getMessage(), e);
							throw new InvocationTargetException(e);
						}
