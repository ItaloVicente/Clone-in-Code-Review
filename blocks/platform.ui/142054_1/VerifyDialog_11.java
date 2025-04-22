		_failureText = "Testing dialog is required, use VerifyDialog::open(Dialog)";
		return IDialogConstants.NO_ID;
	}

	public int open(Dialog testDialog) {
		if (getShell() == null) {
			create();
		}
		getShell().setLocation(0, 0);
		getShell().setSize(Math.max(SIZING_WIDTH, getShell().getSize().x), getShell().getSize().y);
		_testDialog = testDialog;
		if (_testDialog.getShell() == null) {
			_testDialog.create();
		}
		_testDialogSize = _testDialog.getShell().getSize();
		openNewTestDialog();

		return super.open();
	}

	private void openNewTestDialog() {
		if (_testDialog.getShell() == null) {
			_testDialog.create();
		}
		_testDialog.setBlockOnOpen(false);
		_testDialog.getShell().setLocation(getShell().getSize().x + 1, 0);
		_testDialog.getShell().setSize(_testDialogSize);
		_testDialog.getShell().addShellListener(new ShellAdapter() {
			@Override
