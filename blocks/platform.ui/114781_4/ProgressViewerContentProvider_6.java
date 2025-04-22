		@Override
		public void removed(JobTreeElement jte) {
			final JobTreeElement element = jte;
			Job updateJob = new WorkbenchJob("Remove finished") {//$NON-NLS-1$
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (element == null) {
						refresh();
					} else {
						remove(element);
					}
					return Status.OK_STATUS;
				}
			};
			updateJob.setSystem(true);
			updateJob.schedule();
		}
