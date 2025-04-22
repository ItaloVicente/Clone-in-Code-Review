		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final Shell shell = createShell(display);
				GridLayoutFactory.fillDefaults().generateLayout(shell);
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
