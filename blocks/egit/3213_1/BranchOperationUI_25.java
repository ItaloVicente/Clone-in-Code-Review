	private String getTargetWithDialog() {
		AbstractBranchSelectionDialog dialog;
		switch (mode) {
		case MODE_CHECKOUT:
			dialog = new CheckoutDialog(getShell(), repository);
			break;
		case MODE_CREATE:
			dialog = new CreateBranchDialog(getShell(), repository);
			if (dialog.open() != Window.OK)
				return null;
			CreateBranchWizard wiz = new CreateBranchWizard(repository, dialog
					.getRefName());
			new WizardDialog(getShell(), wiz).open();
			return null;
		case MODE_DELETE:
			new DeleteBranchDialog(getShell(), repository).open();
			return null;
		case MODE_RENAME:
			new RenameBranchDialog(getShell(), repository).open();
			return null;
		default:
			return null;
		}
