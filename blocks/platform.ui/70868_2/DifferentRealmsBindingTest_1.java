		new Thread() {
			@Override
			public void run() {
				validationRealm.init(Thread.currentThread());
				validationRealm.block();
			}
		}.start();
