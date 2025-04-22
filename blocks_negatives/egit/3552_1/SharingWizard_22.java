				});
			} catch (InvocationTargetException e) {
				Activator.handleError(UIText.SharingWizard_failed,
						e.getCause(), true);
				return false;
			} catch (InterruptedException e) {
			}
			return true;
		} else {
			final ConnectProviderOperation op = new ConnectProviderOperation(
					existingPage.getProjects(true));
			try {
				getContainer().run(true, false, new IRunnableWithProgress() {
					public void run(final IProgressMonitor monitor)
							throws InvocationTargetException {
						try {
							op.execute(monitor);
							PlatformUI.getWorkbench().getDisplay()
									.syncExec(new Runnable() {
										public void run() {
											Set<File> filesToAdd = new HashSet<File>();
											for (Entry<IProject, File> entry : existingPage
													.getProjects(true)
													.entrySet())
												filesToAdd.add(entry.getValue());
											for (File file : filesToAdd)
												Activator
														.getDefault()
														.getRepositoryUtil()
														.addConfiguredRepository(
																file);
										}
									});
						} catch (CoreException ce) {
							throw new InvocationTargetException(ce);
						}
					}
				});
				return true;
			} catch (Throwable e) {
				if (e instanceof InvocationTargetException) {
					e = e.getCause();
				}
				if (e instanceof CoreException) {
					IStatus status = ((CoreException) e).getStatus();
					e = status.getException();
