			else
				configurePushUriPage.setURI(configureFetchUriPage.getUri());

		if (page == configureFetchUriPage) {
			try {
				getContainer().run(false, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						String taskName = NLS.bind(
								UIText.NewRemoteWizard_CheckingUriTaskName,
								configureFetchUriPage.getUri()
										.toPrivateString());
						monitor.beginTask(taskName, IProgressMonitor.UNKNOWN);
						configureFetchSpecPage
								.setSelection(new RepositorySelection(
										configureFetchUriPage.getUri(), null));
						monitor.done();

					}
				});
			} catch (InvocationTargetException e) {
				Activator.handleError(e.getMessage(), e, true);
			} catch (InterruptedException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		}
