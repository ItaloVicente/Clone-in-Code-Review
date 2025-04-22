	@Override
	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		createCompareInput();
		compareInput.prepareInput(configuration, monitor);
	}

