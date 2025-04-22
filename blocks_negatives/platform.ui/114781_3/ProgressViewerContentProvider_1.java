	/**
	 * Return a listener for kept jobs.
	 *
	 * @return KeptJobsListener
	 */
	private KeptJobsListener getKeptJobListener() {
		keptJobListener = new KeptJobsListener() {

			@Override
			public void finished(JobTreeElement jte) {
				final JobTreeElement element = jte;
					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						refresh(element);
						return Status.OK_STATUS;
					}
