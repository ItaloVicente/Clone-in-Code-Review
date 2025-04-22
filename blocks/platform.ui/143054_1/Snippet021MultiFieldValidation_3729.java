		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			IWizard wizard = new MultiFieldValidationWizard();
			WizardDialog dialog = new WizardDialog(null, wizard);
			dialog.open();

			Display display1 = Display.getCurrent();
			while (dialog.getShell() != null && !dialog.getShell().isDisposed()) {
				if (!display1.readAndDispatch()) {
					display1.sleep();
