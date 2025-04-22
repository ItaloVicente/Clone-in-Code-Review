	private boolean hasReviewNotes(final URIish uri, int timeout,
			UserPasswordCredentials credentials) {
		boolean hasNotes = false;
		try {
			final Repository db = new FileRepository(new File("/tmp")); //$NON-NLS-1$
			final ListRemoteOperation listRemoteOp = new ListRemoteOperation(db, uri, timeout);
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
			String notesRef = Constants.R_NOTES + "review"; //$NON-NLS-1$
			hasNotes = listRemoteOp.getRemoteRef(notesRef) != null;
		} catch (IOException e) {
			Activator.handleError(UIText.GitCloneWizard_failed,
					e, true);
		} catch (InvocationTargetException e) {
			Activator.handleError(UIText.GitCloneWizard_failed,
					e.getCause(), true);
		} catch (InterruptedException e) {
		}
		return hasNotes;
	}

