		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			LabelProviderTest2 test = new LabelProviderTest2();
			Shell s = test.getShell();
			s.pack();
			s.setVisible(true);

			while (!s.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
