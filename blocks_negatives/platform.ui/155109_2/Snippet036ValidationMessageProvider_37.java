			IWizard wizard = new MessageProviderWizard();
			WizardDialog wizardDialog = new WizardDialog(null, wizard);
			wizardDialog.open();

			Display display1 = Display.getCurrent();
			while (wizardDialog.getShell() != null && !wizardDialog.getShell().isDisposed()) {
				if (!display1.readAndDispatch()) {
					display1.sleep();
