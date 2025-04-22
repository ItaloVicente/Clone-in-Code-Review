
	private static final QualifiedName LENGTH_RESOURCE_KEY = new QualifiedName(
			Activator.getPluginId(), "fileLength"); //$NON-NLS-1$

	private static ExceptionCollector exceptions = new ExceptionCollector(
			CoreText.ContainerTreeIterator_exceptionMessage,
			Activator.getPluginId(), IStatus.WARNING, Activator.getDefault()
					.getLog());

