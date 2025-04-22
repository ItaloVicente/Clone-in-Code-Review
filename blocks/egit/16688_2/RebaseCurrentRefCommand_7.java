
	@Override
	public RebaseOperation createRebaseOperation(ExecutionEvent event)
			throws ExecutionException {
		Ref ref;
		ISelection currentSelection = getCurrentSelectionChecked(event);
		if (currentSelection instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) currentSelection;
			Object selected = selection.getFirstElement();
			ref = getRef(selected);
		} else
			ref = null;

		final Repository repository = getRepository(event);
		if (repository == null)
			return null;

		BasicConfigurationDialog.show(repository);

		String currentFullBranch = getFullBranch(repository);
		if (ref != null && ref.getName().equals(currentFullBranch))
			ref = null;

		if (ref == null) {
			RebaseTargetSelectionDialog rebaseTargetSelectionDialog = new RebaseTargetSelectionDialog(
					getShell(event), repository);
			if (rebaseTargetSelectionDialog.open() == IDialogConstants.OK_ID) {
				String refName = rebaseTargetSelectionDialog.getRefName();
				try {
					ref = repository.getRef(refName);
				} catch (IOException e) {
					throw new ExecutionException(e.getMessage(), e);
				}
			} else
				return null;
		}

		jobname = NLS.bind(
				UIText.RebaseCurrentRefCommand_RebasingCurrentJobName,
				Repository.shortenRefName(currentFullBranch), ref);

		return new RebaseOperation(repository, ref);
	}
