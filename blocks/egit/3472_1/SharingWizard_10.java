		if (existingPage.getExternalMode()) {
			try {
				final Map<IProject, File> projectsToMove = existingPage
						.getProjects(true);
				final Repository selectedRepository = existingPage
						.getSelectedRepsoitory();
				getContainer().run(false, false, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						for (Map.Entry<IProject, File> entry : projectsToMove
								.entrySet()) {
							URI targetLocation = entry.getValue().toURI();
							MoveProjectOperation op = new MoveProjectOperation(
									entry.getKey(), targetLocation,
									"Moving project"); //$NON-NLS-1$
							try {
								IStatus result = op.execute(monitor, null);
								if (!result.isOK())
									throw new RuntimeException();
							} catch (ExecutionException e) {
								throw new InvocationTargetException(e);
							}

							try {
								new ConnectProviderOperation(entry.getKey(),
										selectedRepository.getDirectory())
										.execute(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}
					}
				});
			} catch (InvocationTargetException e) {
				Activator.handleError(UIText.SharingWizard_failed,
						e.getCause(), true);
				return false;
			} catch (InterruptedException e) {
			}
			return true;
		}
