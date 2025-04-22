	@Override
	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		super.prepareInput(configuration, monitor);

		configuration.setLeftEditable(true);
	}

