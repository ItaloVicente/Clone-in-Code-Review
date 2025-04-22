		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				try {
					Snippet026AnonymousBeanProperties window = new Snippet026AnonymousBeanProperties();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
