	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public void execute(IProgressMonitor monitor) throws CoreException {
		if (operationResult != null)
			throw new IllegalStateException(CoreText.OperationAlreadyExecuted);

		if (remote.getURIs().isEmpty()) {
			IStatus error = Activator.createErrorStatus(NLS.bind(
					UIText.FetchConfiguredRemoteAction_NoUrisDefinedMessage,
					remote.getName()), null);
			throw new CoreException(error);
		}

		IProgressMonitor actMonitor = monitor;
		if (actMonitor == null)
			actMonitor = new NullProgressMonitor();

