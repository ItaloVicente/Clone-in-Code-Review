				final JobTreeElement element = jte;
					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						if (element == null) {
							refresh();
						} else {
							ProgressViewerContentProvider.this
									.remove(new Object[] { element });
						}
						return Status.OK_STATUS;
					}
				};
				updateJob.setSystem(true);
				updateJob.schedule();

