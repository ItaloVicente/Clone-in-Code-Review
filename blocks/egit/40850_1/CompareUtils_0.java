	private static void compareWorkspaceWithRef(final Repository repository,
			final IFile file, final String refName, final IWorkbenchPage page) {

		Job job = new Job(UIText.CompareUtils_jobName) {

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				final RepositoryMapping mapping = RepositoryMapping
						.getMapping(file);
				final String gitPath = mapping.getRepoRelativePath(file);
				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement(file);

				CompareEditorInput in;
				try {
					in = prepareCompareInput(repository, gitPath, base, refName);
				} catch (IOException e) {
					return Activator.createErrorStatus(
							UIText.CompareWithRefAction_errorOnSynchronize, e);
				}

				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				openCompareEditorRunnable(page, in);
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}
