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
