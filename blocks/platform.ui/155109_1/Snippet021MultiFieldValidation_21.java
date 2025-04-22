	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			IWizard wizard = new MultiFieldValidationWizard();
			WizardDialog dialog = new WizardDialog(null, wizard);
			dialog.open();

			while (!dialog.getShell().isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

