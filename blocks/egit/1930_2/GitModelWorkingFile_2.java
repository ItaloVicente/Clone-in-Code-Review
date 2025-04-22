	@Override
	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		super.prepareInput(configuration, monitor);
		configuration.setLeftLabel(NLS.bind(
				UIText.GitModelWorkingFile_localVersion, getLeft().getName()));
	}

