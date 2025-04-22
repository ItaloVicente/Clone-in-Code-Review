			try {
				getContainer().run(false, false, new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						String taskName = NLS.bind(
								UIText.NewRemoteWizard_CheckingUriTaskName,
								configurePushUriPage.getAllUris().get(0)
										.toPrivateString());
						monitor.beginTask(taskName, IProgressMonitor.UNKNOWN);
						configurePushSpecPage.setConfigName(myRemoteName);
						configurePushSpecPage
								.setSelection(new RepositorySelection(
										configurePushUriPage.getAllUris()
												.get(0), null));

					}
				});
			} catch (InvocationTargetException e) {
				Activator.handleError(e.getMessage(), e, true);
			} catch (InterruptedException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
