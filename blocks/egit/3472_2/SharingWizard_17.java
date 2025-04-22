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

							IPath targetLocation = new Path(entry.getValue()
									.getPath());
							IPath currentLocation = entry.getKey()
									.getLocation();
							if (!targetLocation.equals(currentLocation)) {
								MoveProjectOperation op = new MoveProjectOperation(
										entry.getKey(),
										entry.getValue().toURI(),
										UIText.SharingWizard_MoveProjectActionLabel);
								try {
									IStatus result = op.execute(monitor, null);
									if (!result.isOK())
										throw new RuntimeException();
								} catch (ExecutionException e) {
									if (e.getCause() != null)
										throw new InvocationTargetException(e
												.getCause());
									throw new InvocationTargetException(e);
								}
							}
							try {
								new ConnectProviderOperation(entry.getKey(),
										selectedRepository.getDirectory())
										.execute(monitor);
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}
