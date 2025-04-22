		validationRealm.exec(new Runnable() {
			@Override
			public void run() {
				dbc.dispose();
			}
		});
		validationRealm.processQueue();
		targetAndModelRealm.unblock();
		validationRealm.unblock();
