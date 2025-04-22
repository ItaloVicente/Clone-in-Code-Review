			public Adapter caseDialogElement(MDialogElement object) {
				return createDialogElementAdapter();
			}
			@Override
			public Adapter caseWizardElement(MWizardElement object) {
				return createWizardElementAdapter();
			}
			@Override
