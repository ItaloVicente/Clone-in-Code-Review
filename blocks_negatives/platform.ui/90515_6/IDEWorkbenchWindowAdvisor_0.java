								StatusManager
										.getManager()
										.handle(
												new Status(
														IStatus.ERROR,
														IDEApplication.PLUGIN_ID,
														"Failed to load feature", exception));//$NON-NLS-1$
