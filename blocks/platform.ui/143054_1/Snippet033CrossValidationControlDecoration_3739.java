		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			createContents();
			shell.pack();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
