	private JobChangeAdapter consumeEventLoopOnJobDone = new JobChangeAdapter() {
		@Override
		public void done(IJobChangeEvent event) {
			PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch()) {
				}
			});
		}
	};
