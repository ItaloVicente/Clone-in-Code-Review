	private String getTargetWithDialog() {
		AbstractBranchSelectionDialog dialog;
		switch (mode) {
		case MODE_BRANCH:
			dialog = new BranchSelectionDialog(getShell(), repository);
			break;
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
		default:
			return null;
		}

		if (dialog.open() != Window.OK) {
			return null;
		}
		return dialog.getRefName();
	}

