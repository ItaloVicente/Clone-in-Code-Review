	private void openResultDialog(Job job) {
		assertNotNull("Job should not be null", job);
		final Action action = (Action) job
				.getProperty(IProgressConstants.ACTION_PROPERTY);
		if (action != null) {
			UIThreadRunnable.asyncExec(new VoidResult() {

				@Override
				public void run() {
					action.run();
				}
			});
		}
	}

