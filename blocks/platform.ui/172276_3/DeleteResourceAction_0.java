		@Override
		protected void okPressed() {
			fDoNotShowConfirmDialog = fDontShowAgain.getSelection();
			EditorsPreferencePage.setDontShowDeleteConfirmAgain(fDoNotShowConfirmDialog);
			super.okPressed();
		}

