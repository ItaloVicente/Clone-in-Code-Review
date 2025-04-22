		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				try {
					Snippet024SelectObservableValue window = new Snippet024SelectObservableValue();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
