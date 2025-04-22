		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				createContents();
				shell.open();
				shell.layout();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
