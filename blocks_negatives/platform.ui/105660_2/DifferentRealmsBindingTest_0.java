		new Thread() {
			@Override
			public void run() {
				targetAndModelRealm.init(Thread.currentThread());
				targetAndModelRealm.block();
			}
		}.start();
