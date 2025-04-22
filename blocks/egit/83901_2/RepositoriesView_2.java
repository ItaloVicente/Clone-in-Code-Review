					public boolean belongsTo(Object family) {
						return JobFamilies.REPO_VIEW_REFRESH.equals(family);
					}

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor1) {
						if (monitor1.isCanceled() || !UIUtils.isUsable(tv)) {
							return Status.CANCEL_STATUS;
