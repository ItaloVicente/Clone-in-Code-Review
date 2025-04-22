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

		display.dispose();
	}

	private static Shell createShell() {
		IWizard wizard = new MultiFieldValidationWizard();
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.open();

		return dialog.getShell();
	}

