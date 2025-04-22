		getRealm().exec(new Runnable() {
			@Override
			public void run() {
				if (hasListeners()) {
					getValue();
				}
