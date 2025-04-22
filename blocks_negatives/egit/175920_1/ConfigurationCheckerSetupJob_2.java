	public static void checkConfiguration() {
		Job job = new Job(UIText.ConfigurationChecker_checkConfiguration) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (PlatformUI.isWorkbenchRunning()) {
					PlatformUI.getWorkbench().getDisplay()
							.asyncExec(new Runnable() {
								@Override
								public void run() {
									check();
								}
							});
				} else {
					schedule(1000L);
