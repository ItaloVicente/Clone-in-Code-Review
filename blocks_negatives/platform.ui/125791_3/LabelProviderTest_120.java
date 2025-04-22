		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			LabelProviderTest test = new LabelProviderTest();
			Shell s = test.getShell();
			s.pack();
			s.setVisible(true);

			while (!s.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
