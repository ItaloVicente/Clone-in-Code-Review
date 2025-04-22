	private final class UpdateSelectionJob extends Job {
		private ISelection sel;

		private UpdateSelectionJob() {
			super(UIText.RepositorySourceProvider_updateRepoSelection);
			setSystem(true);
			setUser(false);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			ISelection s;
			synchronized (this) {
				s = sel;
				sel = null;
			}
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}
			Repository newRepository;
			if (s == null || s.isEmpty()) {
				newRepository = null;
			} else {
				IStructuredSelection ss = SelectionUtils
						.getStructuredSelection(s);
				if (ss.isEmpty()) {
					newRepository = null;
				} else {
					newRepository = SelectionUtils.getRepository(ss);
				}
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

		public void schedule(ISelection selection) {
			cancel();
			synchronized (this) {
				sel = selection;
			}
			schedule();
		}
	}

