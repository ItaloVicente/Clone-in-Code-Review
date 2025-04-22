			public Adapter caseWizard(MWizard object) {
				return createWizardAdapter();
			}
			@Override
			public Adapter caseDialogElement(MDialogElement object) {
				return createDialogElementAdapter();
			}
			@Override
			public Adapter caseWizardElement(MWizardElement object) {
				return createWizardElementAdapter();
