	private Job backgroundWork = new Job(
			UIText.ConfigurationChecker_checkConfiguration) {

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			ConfigurationChecker.checkConfiguration();
			return Status.OK_STATUS;
		}
	};

