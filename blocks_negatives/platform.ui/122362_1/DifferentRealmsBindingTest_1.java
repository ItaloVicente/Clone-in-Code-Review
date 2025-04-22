		initRealm(targetRealm, true);
		initRealm(modelRealm, true);
		initRealm(validationRealm, false);
		mainThread.init(Thread.currentThread());

		dbc = new DataBindingContext(validationRealm);
		Policy.setLog(logger);
	}

	private void initRealm(final ThreadRealm realm, final boolean block) {
