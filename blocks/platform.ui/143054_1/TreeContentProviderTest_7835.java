		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			TreeContentProviderTest test = new TreeContentProviderTest();
			Shell s = test.getShell();
			s.pack();
			s.setVisible(true);

			while (!s.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
