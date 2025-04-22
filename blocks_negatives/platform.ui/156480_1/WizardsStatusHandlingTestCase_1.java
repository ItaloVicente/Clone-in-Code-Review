		dialog.setBlockOnOpen(false);
		dialog.open();

		UITestCase.processEvents();

		IWizardPage currenPage = dialog.getCurrentPage();
		Composite control = (Composite) currenPage.getControl();
		Control[] widgets = control.getChildren();

		Table table = (Table) widgets[1];

		for (int i = 0; i < table.getItemCount(); i++) {
			if (table.getItem(i).getText().equals(FAULTY_WIZARD_NAME)) {
				table.select(i);
				table.notifyListeners(SWT.Selection, new Event());
				UITestCase.processEvents();
				break;
