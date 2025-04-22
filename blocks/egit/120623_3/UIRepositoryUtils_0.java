
	public static boolean showCleanupDialog(Repository repo, List<String> files,
			String title, Shell shell) {
		Collections.sort(files);
		CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
				shell, title,
				UIText.AbstractRebaseCommandHandler_cleanupDialog_text, repo,
				files);
		cleanupUncomittedChangesDialog.open();
		return cleanupUncomittedChangesDialog.shouldContinue();
	}

