		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			try {
				Snippet024SelectObservableValue window = new Snippet024SelectObservableValue();
				window.open();
			} catch (Exception e) {
				e.printStackTrace();
