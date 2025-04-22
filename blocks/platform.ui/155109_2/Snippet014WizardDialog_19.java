	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});
	}

	public static Shell createShell() {
		IWizard wizard = new SampleWizard();
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.open();

		return dialog.getShell();
	}

