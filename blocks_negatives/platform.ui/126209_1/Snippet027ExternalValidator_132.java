		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				IWizard wizard = new ExternalValidationWizard();
				WizardDialog dialog = new WizardDialog(null, wizard);
				dialog.open();

				Display display = Display.getCurrent();
				while (dialog.getShell() != null
						&& !dialog.getShell().isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
