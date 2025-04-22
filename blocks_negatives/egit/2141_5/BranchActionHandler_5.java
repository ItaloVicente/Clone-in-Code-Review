
		if (!repository.getRepositoryState().canCheckout()) {
			MessageDialog.openError(getShell(event),
					UIText.BranchAction_cannotCheckout, NLS.bind(
							UIText.BranchAction_repositoryState, repository
									.getRepositoryState().getDescription()));
			return null;
		}

		BranchSelectionDialog dialog = new BranchSelectionDialog(
				getShell(event), repository);
		if (dialog.open() != Window.OK) {
			return null;
		}

		final String refName = dialog.getRefName();

		String jobname = NLS.bind(UIText.BranchAction_checkingOut, refName);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new BranchOperation(repository, refName).execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.BranchAction_branchFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
