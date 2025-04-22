
	/**
	 * @param shell
	 * @param repository
	 * @param result
	 * @param target
	 */
	private BranchResultDialog(Shell shell, Repository repository,
			CheckoutResult result, String target) {
		super(shell, UIText.BranchResultDialog_CheckoutConflictsTitle, INFO,
				NLS.bind(UIText.BranchResultDialog_CheckoutConflictsMessage,
						Repository.shortenRefName(target)),
				MessageDialog.INFORMATION,
				new String[] { IDialogConstants.OK_LABEL }, 0);
		setShellStyle(getShellStyle() | SWT.SHELL_TRIM);
		this.repository = repository;
		this.result = result;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().indent(0, 0).grab(true, true)
				.applyTo(main);
		new NonDeletedFilesTree(main, repository, this.result.getConflictList());
		applyDialogFont(main);

		return main;
	}

	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.PROCEED_ID:
			CommonUtils.runCommand(CommitCommand.ID, new StructuredSelection(
					new RepositoryNode(null, repository)));
			break;
		case IDialogConstants.ABORT_ID:
			CommonUtils.runCommand(ResetCommand.ID, new StructuredSelection(
					new RepositoryNode(null, repository)));
			break;
		case IDialogConstants.SKIP_ID:
			CommonUtils.runCommand(StashCreateCommand.ID,
					new StructuredSelection(
							new RepositoryNode(null, repository)));
			break;
		}
		super.buttonPressed(buttonId);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		createButton(parent, IDialogConstants.ABORT_ID,
				UIText.BranchResultDialog_buttonReset, false);
		createButton(parent, IDialogConstants.PROCEED_ID,
				UIText.BranchResultDialog_buttonCommit, false);
		createButton(parent, IDialogConstants.SKIP_ID,
				UIText.BranchResultDialog_buttonStash, false);
	}
}
