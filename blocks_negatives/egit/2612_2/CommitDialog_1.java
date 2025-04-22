	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(UIText.CommitDialog_CommitChanges);

		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
