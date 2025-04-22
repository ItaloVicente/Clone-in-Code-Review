
	private boolean handleUncommittedFiles(Repository repo, Shell shell,
			String repoName) throws GitAPIException {
		try (Git git = new Git(repo)) {
			org.eclipse.jgit.api.Status status = git.status().call();

			if (status.hasUncommittedChanges()) {
				List<String> files = new ArrayList<String>(
						status.getUncommittedChanges());
				Collections.sort(files);
				CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
						shell,
						MessageFormat
								.format(UIText.FeatureCheckoutHandler_cleanupDialog_title,
										repoName),
						UIText.FeatureCheckoutHandler_cleanupDialog_text,
						repo, files);
				cleanupUncomittedChangesDialog.open();
				return cleanupUncomittedChangesDialog.shouldContinue();
			} else {
				return true;
			}
		}
	}
