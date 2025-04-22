	private void loadExtensions() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(DIALOG_EP_ID);
		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("commitDialog"); //$NON-NLS-1$
				if (o instanceof ICommitDialogExtender) {
					ISafeRunnable runnable = new ISafeRunnable() {
						public void handleException(Throwable exception) {
						}

						public void run() throws Exception {
							StringBuilder sb = new StringBuilder();

							sb.append(commitMessage);
							sb.append(((ICommitDialogExtender) o)
									.getCommitMessage());
							sb.append(System
									.getProperty(Platform.PREF_LINE_SEPARATOR));

							commitMessage = sb.toString();
						}
					};
					SafeRunner.run(runnable);
				}
			}
		} catch (CoreException ex) {
		}

	}

