	private void setValidationStatus(final IStatus status) {
		validationStatusObservable.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				validationStatusObservable.setValue(status);
			}
		});
	}

