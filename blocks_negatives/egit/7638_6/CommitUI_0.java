	private void pushWhenFinished(Job commitJob) {
		commitJob.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().getSeverity() == IStatus.ERROR)
					return;
				RemoteConfig config = SimpleConfigurePushDialog
						.getConfiguredRemote(repo);
				if (config == null) {
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							try {
								WizardDialog wizardDialog = new WizardDialog(
										shell, new PushWizard(repo));
								wizardDialog.setHelpAvailable(true);
								wizardDialog.open();
							} catch (URISyntaxException e) {
								Activator.handleError(NLS.bind(
										UIText.CommitUI_pushFailedMessage, e),
										e, true);
							}
						}
					});
				} else {
					int timeout = Activator.getDefault().getPreferenceStore()
							.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
					PushOperationUI op = new PushOperationUI(repo, config
							.getName(), timeout, false);
					op.start();
				}
			}
		});
	}

	/**
	 * Uses a Job to perform the given CommitOperation
	 * @param repository
	 * @param commitOperation
	 * @param openNewCommit
	 */
	public static void performCommit(final Repository repository,
			final CommitOperation commitOperation, final boolean openNewCommit) {
		Job job = createCommitJob(repository, commitOperation, openNewCommit);
		job.schedule();
	}

	private static Job createCommitJob(final Repository repository,
			final CommitOperation commitOperation, final boolean openNewCommit) {
		String jobname = UIText.CommitAction_CommittingChanges;
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				RevCommit commit = null;
				try {
					commitOperation.execute(monitor);
					commit = commitOperation.getCommit();
					CommitMessageComponentStateManager.deleteState(
							repository);
					RepositoryMapping mapping = RepositoryMapping
							.findRepositoryMapping(repository);
					if (mapping != null)
						mapping.fireRepositoryChanged();
				} catch (CoreException e) {
					if (e.getCause() instanceof JGitInternalException)
						return Activator.createErrorStatus(
								e.getLocalizedMessage(), e.getCause());
					return Activator.createErrorStatus(
							UIText.CommitAction_CommittingFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				if (openNewCommit && commit != null)
					openCommit(repository, commit);
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.COMMIT))
					return true;
				return super.belongsTo(family);
			}

		};
		job.setUser(true);
		return job;
	}

	private static void openCommit(final Repository repository,
			final RevCommit newCommit) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				CommitEditor.openQuiet(new RepositoryCommit(repository,
						newCommit));
			}
		});
	}

