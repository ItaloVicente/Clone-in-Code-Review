		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			final Shell shell = createShell(display);
			GridLayoutFactory.fillDefaults().generateLayout(shell);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
