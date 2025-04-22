		this(shell, dialogTitle, dialogMessage, repository, fileList, true);
	}

	public CleanupUncomittedChangesDialog(Shell shell, String title,
			String message, Repository repository, List<String> fileList,
			boolean needResult) {
		super(shell, title, INFO, message,
