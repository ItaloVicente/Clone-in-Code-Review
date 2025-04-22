	/* copy of {@link org.eclipse.egit.ui.internal.repository.tree.command.MergeCommand#canMerge(Repository)}
	 * @param repository
	 * @return true of merge is allowed */
	private boolean canMerge(final Repository repository) {
		String message = null;
		Exception ex = null;
		try {
			Ref head = repository.getRef(Constants.HEAD);
			if (head == null || !head.isSymbolic())
				message = UIText.MergeAction_HeadIsNoBranch;
			else if (!repository.getRepositoryState().equals(
					RepositoryState.SAFE))
				message = NLS.bind(UIText.MergeAction_WrongRepositoryState,
						repository.getRepositoryState());
		} catch (IOException e) {
			message = e.getMessage();
			ex = e;
		}

		if (message != null)
			Activator.handleError(UIText.MergeAction_CannotMerge, ex, true);
		return (message == null);
	}

