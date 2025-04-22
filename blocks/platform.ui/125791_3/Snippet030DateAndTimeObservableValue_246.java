		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				createContents();
				shell.pack();
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
