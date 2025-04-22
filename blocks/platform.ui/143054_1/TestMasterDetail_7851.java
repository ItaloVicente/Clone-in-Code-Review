		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			createShell();
			bind(shell);

			shell.setSize(600, 600);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
