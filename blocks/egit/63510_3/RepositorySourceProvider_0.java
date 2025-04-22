		final ISelection sel = selection;
		updateSelectionJob = new Job(
				UIText.RepositorySourceProvider_updateRepoSelection) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				Repository newRepository;
				if (sel == null) {
					newRepository = null;
				} else {
					newRepository = SelectionUtils.getRepository(
							SelectionUtils.getStructuredSelection(sel));
				}
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				if (repository != newRepository) {
					repository = newRepository;
					fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
							REPOSITORY_PROPERTY, repository);
				}
				return Status.OK_STATUS;
			}

		};
		updateSelectionJob.setSystem(true);
		updateSelectionJob.schedule();
