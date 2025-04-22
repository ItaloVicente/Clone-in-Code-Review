		managedForm.addPart(new FocusManagerFormPart(focusTracker) {

			@Override
			public void setDefaultFocus() {
				getManagedForm().getForm().setFocus();
			}
		});
