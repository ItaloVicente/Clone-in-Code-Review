		validationStatusObservable.getRealm().exec(new Runnable() {
			@Override
			public void run() {
				validationStatusObservable.setValue(status);
			}
		});
