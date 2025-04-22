
	/**
	 * @param parentShell
	 */
	public CommitDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.SELECT_ALL_ID, UIText.CommitDialog_SelectAll, false);
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, UIText.CommitDialog_DeselectAll, false);

		createButton(parent, IDialogConstants.OK_ID, UIText.CommitDialog_Commit, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

