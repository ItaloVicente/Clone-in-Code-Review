		final ISelection sel = selection;
		Job job = new Job(UIText.RepositorySourceProvider_updateRepoSelection) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				Repository newRepository;
				if (sel == null) {
					newRepository = null;
				} else {
					newRepository = SelectionUtils.getRepository(
							SelectionUtils.getStructuredSelection(sel));
				}
				if (repository != newRepository) {
					repository = newRepository;
					fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
							REPOSITORY_PROPERTY, repository);
				}
				return Status.OK_STATUS;
			}

		};
		job.schedule();
