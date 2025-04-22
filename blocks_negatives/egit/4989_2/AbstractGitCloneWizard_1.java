	private boolean hasReviewNotes(final URIish uri, int timeout,
			UserPasswordCredentials credentials) {
		boolean hasNotes = false;
		try {
			final ListRemoteOperation listRemoteOp = new ListRemoteOperation(
					db, uri, timeout);
			if (credentials != null)
				listRemoteOp
						.setCredentialsProvider(new EGitCredentialsProvider(
								credentials.getUser(), credentials
										.getPassword()));
			getContainer().run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					listRemoteOp.run(monitor);
				}
			});
			hasNotes = listRemoteOp.getRemoteRef(notesRef) != null;
		} catch (IOException e) {
			Activator.handleError(UIText.GitCloneWizard_failed, e, true);
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.GitCloneWizard_failed, e.getCause(),
					true);
		} catch (InterruptedException e) {
		}
		return hasNotes;
	}

	private void doGerritConfiguration(final String remoteName,
			final CloneOperation op, final boolean hasNotes) {
		String gerritBranch = gerritConfiguration.getBranch();
		URIish pushURI = gerritConfiguration.getURI();
		if (hasNotes) {
			op.addPostCloneTask(new ConfigureFetchAfterCloneTask(remoteName,
		}
		if (gerritBranch != null && gerritBranch.length() > 0) {
			ConfigurePushAfterCloneTask push = new ConfigurePushAfterCloneTask(
					remoteName, "HEAD:refs/for/" + gerritBranch, pushURI); //$NON-NLS-1$
			op.addPostCloneTask(push);
		}
		op.addPostCloneTask(new SetChangeIdTask(true));
	}

