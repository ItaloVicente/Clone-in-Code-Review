	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			IWizard wizard = new ExternalValidationWizard();
			WizardDialog dialog = new WizardDialog(null, wizard);
			dialog.open();
		});

		display.dispose();
	}

