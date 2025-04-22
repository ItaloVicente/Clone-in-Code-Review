	public static final String DEFAULT_CANCEL_DIALOG_MESSAGE = UIText.RebaseCurrentRefCommand_RebaseCanceledMessage;

	public static void runRebaseJob(final Repository repository,
			String jobname, Ref ref, final String dialogMessage) {
		runRebaseJob(repository, jobname, ref, Operation.BEGIN, false,
				dialogMessage, null);
	}

