		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				UIStartupTest.super.testGetFirstPart_GetContext();
			}
		});
