		threadRealm.exec(new Runnable() {
			@Override
			public void run() {
				bean.setValue("newerValue");
			}
		});
