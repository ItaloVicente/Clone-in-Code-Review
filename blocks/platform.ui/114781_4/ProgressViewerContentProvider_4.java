		@Override
		public void finished(JobTreeElement jte) {
			final JobTreeElement element = jte;
			Job updateJob = new WorkbenchJob("Refresh finished") {//$NON-NLS-1$
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					refresh(element);
					return Status.OK_STATUS;
				}
