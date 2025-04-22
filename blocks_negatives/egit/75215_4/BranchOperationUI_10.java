	private String getTargetWithDialog() {
		final String[] dialogResult = new String[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				dialogResult[0] = getTargetWithDialogInUI();
			}
		});
		return dialogResult[0];
	}

	private String getTargetWithDialogInUI() {
		AbstractBranchSelectionDialog dialog;
		switch (mode) {
		case MODE_CHECKOUT:
			dialog = new CheckoutDialog(getShell(), repository);
			break;
		case MODE_CREATE:
			CreateBranchWizard wiz;
			try {
				if (base == null)
					base = repository.getFullBranch();
				wiz = new CreateBranchWizard(repository, base);
			} catch (IOException e) {
				wiz = new CreateBranchWizard(repository);
			}
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

		if (dialog.open() != Window.OK) {
			return null;
		}
		return dialog.getRefName();
	}

