		if (sel.size() > 0) {
			WorkbenchWizardElement selectedWizard = (WorkbenchWizardElement) sel.getFirstElement();
			getDialogSettings().put(STORE_SELECTED_WIZARD_ID, selectedWizard.getId());
		}
	}

	@Override
