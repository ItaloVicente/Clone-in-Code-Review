		@Override
		public Adapter caseDialog(MDialog object) {
			return createDialogAdapter();
		}

		@Override
		public Adapter caseWizardDialog(MWizardDialog object) {
			return createWizardDialogAdapter();
		}

