	private RebaseResultDialog(Shell shell, Repository repository,
			RebaseResult result) {
		super(shell, UIText.RebaseResultDialog_DialogTitle, INFO, NLS.bind(
				UIText.RebaseResultDialog_StatusLabel, result.getStatus()
						.name()), MessageDialog.INFORMATION,
				new String[] { IDialogConstants.OK_LABEL }, 0);
