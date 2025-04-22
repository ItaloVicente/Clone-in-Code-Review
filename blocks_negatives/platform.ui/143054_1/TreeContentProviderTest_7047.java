		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				TreeContentProviderTest test = new TreeContentProviderTest();
				Shell s = test.getShell();
				s.pack();
				s.setVisible(true);

				while (!s.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
