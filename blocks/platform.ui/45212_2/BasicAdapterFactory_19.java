			public Adapter caseDialogElement(MDialogElement object) {
				return createDialogElementAdapter();
			}
			@Override
			public Adapter caseWizardElement(MWizardElement object) {
				return createWizardElementAdapter();
			}
			@Override
			public <T extends MUIElement> Adapter caseFrame(MFrame<T> object) {
				return createFrameAdapter();
			}
			@Override
