				final JobTreeElement element = jte;
					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						refresh(new Object[] { element });
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

