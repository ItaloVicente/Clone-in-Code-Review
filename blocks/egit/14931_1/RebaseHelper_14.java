	public static void runRebaseJob(final Repository repository,
			String jobname, final Ref ref, final Operation operation,
			boolean interactive,
			final String dialogMessage,
			final List<RebaseCommandFinishedListener> listeners) {

		final RebaseOperation rebase;

		if (operation == Operation.BEGIN) {
			if (ref == null)
				return; // throw RuntimeException instead?
			if (interactive) {
				InteractiveHandler handler = new ModifyCommitMessageInteractiveHandler(
						repository);
				rebase = new RebaseOperation(repository, ref, handler);
			} else {
				rebase = new RebaseOperation(repository, ref);
			}
		} else if (interactive) {
			InteractiveHandler handler = new ModifyCommitMessageInteractiveHandler(
					repository);
			rebase = new RebaseOperation(repository, operation, handler);
		} else {
			rebase = new RebaseOperation(repository, operation);
		}

