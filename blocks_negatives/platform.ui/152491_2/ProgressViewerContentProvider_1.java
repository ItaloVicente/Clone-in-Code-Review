	class FinishedJobsListener implements KeptJobsListener {

		@Override
		public void finished(JobTreeElement jte) {
			final JobTreeElement element = jte;
				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					refresh(element);
					return Status.OK_STATUS;
				}

				@Override
				public boolean shouldSchedule() {
					return !progressViewer.getControl().isDisposed();
				}

				@Override
				public boolean shouldRun() {
					return !progressViewer.getControl().isDisposed();
				}
			};
			updateJob.setSystem(true);
			updateJob.schedule();
		}

		@Override
		public void removed(JobTreeElement jte) {
			final JobTreeElement element = jte;
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
	}

