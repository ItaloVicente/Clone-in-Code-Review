	/**
	 * Checks if merge is possible:
	 * <ul>
	 * <li>HEAD must point to a branch</li>
	 * <li>Repository State must be SAFE</li>
	 * </ul>
	 *
	 * @param repository
	 * @param event
	 * @return a boolean indicating if merge is possible
	 * @throws ExecutionException
	 */
	protected boolean canMerge(final Repository repository, ExecutionEvent event)
			throws ExecutionException {
		String message = null;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head == null || !head.isSymbolic())
				message = UIText.MergeAction_HeadIsNoBranch;
			else if (!repository.getRepositoryState().equals(
					RepositoryState.SAFE))
				message = NLS.bind(UIText.MergeAction_WrongRepositoryState,
						repository.getRepositoryState());
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
			message = e.getMessage();
		}

		if (message != null)
			MessageDialog.openError(getShell(event),
					UIText.MergeAction_CannotMerge, message);
		return (message == null);
	}
