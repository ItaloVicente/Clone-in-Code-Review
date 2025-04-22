
	/**
	 * Handle exceptions that occur in the decorator.
	 *
	 * @param repository
	 *            The repository that triggered the exception
	 * @param e
	 *            The exception that occurred
	 */
	private void handleException(GitFlowRepository repository, Exception e) {
		if (repository != null) {
			log.log(Activator.error(e.getMessage(), e));
		 }
	}
