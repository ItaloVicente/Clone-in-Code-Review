		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				final Shell shell = new View(model).createShell();
				Display display = Display.getCurrent();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
