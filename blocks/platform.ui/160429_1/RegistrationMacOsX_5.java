
	private final class LsRegisterJob extends Job {
		private LsRegisterJob(String name) {
			super(name);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				setLsRegisterOutput();
			} catch (Exception e) {
			}
			return Status.OK_STATUS;
		}
	}

