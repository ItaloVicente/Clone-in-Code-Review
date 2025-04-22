		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				IWizard wizard = new MessageProviderWizard();
				WizardDialog wizardDialog = new WizardDialog(null, wizard);
				wizardDialog.open();

				Display display = Display.getCurrent();
				while (wizardDialog.getShell() != null
						&& !wizardDialog.getShell().isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
