	@Nullable
	public Repository waitFor() {
		UpdateSelectionJob job = updateSelectionJob;
		if (job == null || !updateInProgress) {
			return repository;
		}

		if (Display.getCurrent() == null) {
			while (updateInProgress) {
				try {
					Job.getJobManager().join(RepositorySourceProvider.class,
							new NullProgressMonitor());
				} catch (OperationCanceledException | InterruptedException e) {
					break;
				}
			}
			return repository;
		}

		IProgressService service = PlatformUI.getWorkbench()
				.getService(IProgressService.class);
		final AtomicBoolean cancelled = new AtomicBoolean(false);
		while (updateInProgress && !cancelled.get()) {
			try {
				service.run(false, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						if (monitor.isCanceled()) {
							cancelled.set(true);
							return;
						}
						Job.getJobManager().join(RepositorySourceProvider.class,
								monitor);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				break;
			}
		}
		return repository;
	}

	private final class UpdateSelectionJob extends Job {
		private IStructuredSelection sel;

		private UpdateSelectionJob() {
			super(UIText.RepositorySourceProvider_updateRepoSelection);
			setSystem(true);
			setUser(false);
		}

		@Override
		public boolean belongsTo(Object family) {
			return RepositorySourceProvider.class == family;
		}

		public void schedule(IStructuredSelection selection) {
			if (selection.isEmpty()) {
				boolean needUpdate;
				synchronized (this) {
					needUpdate = repository != null;
					repository = null;
				}
				if (needUpdate) {
					fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
							REPOSITORY_PROPERTY, null);
				}
				return;
			}

			cancel();

			synchronized (this) {
				updateInProgress = true;
				sel = selection;
			}
			schedule();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStructuredSelection s;
			synchronized (this) {
				s = sel;
				sel = null;
			}
			if (s == null || monitor.isCanceled()) {
				return cancelled();
			}
			Repository newRepository = SelectionUtils.getRepository(s);
			boolean needUpdate;
			synchronized (this) {
				needUpdate = repository != newRepository;
				repository = newRepository;
				updateInProgress = false;
			}
			if (needUpdate) {
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY, newRepository);
			}
			return Status.OK_STATUS;
		}

		IStatus cancelled() {
			synchronized (this) {
				updateInProgress = false;
			}
			return Status.CANCEL_STATUS;
		}
	}
