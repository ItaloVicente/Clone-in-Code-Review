	@Override
	protected void contentsCreated() {
		super.contentsCreated();
		getNavigator().selectChange(true);
	}

	@Override
	protected void handleDispose() {
		super.handleDispose();
		PlatformUI.getWorkbench().getDisplay().asyncExec(this::cleanUp);
	}

	private void cleanUp() {
		if (toDelete == null || toDelete.isEmpty()) {
			return;
		}
		List<IFile> toClean = toDelete;
		toDelete = null;
		Job job = new Job(UIText.GitMergeEditorInput_ResourceCleanupJobName) {

			@Override
			public boolean shouldSchedule() {
				return super.shouldSchedule()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			public boolean shouldRun() {
				return super.shouldRun()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspaceRunnable remove = m -> {
					SubMonitor progress = SubMonitor.convert(m, toClean.size());
					for (IFile tmp : toClean) {
						if (PlatformUI.getWorkbench().isClosing()) {
							return;
						}
						try {
							tmp.delete(true, progress.newChild(1));
						} catch (CoreException e) {
						}
					}
				};
				try {
					ResourcesPlugin.getWorkspace().run(remove, null,
							IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

