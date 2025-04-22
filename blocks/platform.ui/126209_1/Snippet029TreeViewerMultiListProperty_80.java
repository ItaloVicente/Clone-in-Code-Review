		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			createContents();
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
