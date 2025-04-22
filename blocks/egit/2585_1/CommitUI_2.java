		List<CommitObserver> commitObserver = new ArrayList<CommitObserver>();
		IConfigurationElement[] rawExtensions = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.egit.ui.commitObserver"); //$NON-NLS-1$
		for (IConfigurationElement extension : rawExtensions) {
			try {
				Object executableExtension = extension.createExecutableExtension("providerClass"); //$NON-NLS-1$
				if (executableExtension instanceof CommitObserver) {
					commitObserver.add( (CommitObserver)executableExtension );
				}

			} catch (CoreException e) {
			}
		}

