	private static void openCommit(final Repository repository,
			final RevCommit newCommit) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			public void run() {
				CommitEditor.openQuiet(new RepositoryCommit(repository,
						newCommit));
			}
		});
	}

