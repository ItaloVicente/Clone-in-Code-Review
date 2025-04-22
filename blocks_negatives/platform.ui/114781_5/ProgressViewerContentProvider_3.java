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
