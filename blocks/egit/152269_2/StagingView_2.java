			if (files.isEmpty()) {
				return;
			}
			if (!CommandConfirmation.confirmCheckout(form.getShell(), repo)) {
				return;
			}
			DiscardChangesOperation operation = new DiscardChangesOperation(
					repo, files, headRevision ? Constants.HEAD : null);
			JobChangeAdapter refresher = null;
			if (!inaccessibleFiles.isEmpty()) {
				refresher = new JobChangeAdapter() {

					@Override
					public void done(IJobChangeEvent event) {
						IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
								.getDefault().getIndexDiffCache()
								.getIndexDiffCacheEntry(repo);
						if (indexDiffCacheForRepository != null) {
							indexDiffCacheForRepository
									.refreshFiles(inaccessibleFiles);
						}
					}
				};
			}
			JobUtil.scheduleUserWorkspaceJob(operation,
					UIText.DiscardChangesAction_discardChanges,
					JobFamilies.DISCARD_CHANGES, refresher);
