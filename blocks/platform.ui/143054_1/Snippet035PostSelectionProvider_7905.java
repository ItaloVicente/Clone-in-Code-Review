		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Snippet035PostSelectionProvider().createShell();
			Display display1 = Display.getCurrent();
			while (!shell.isDisposed()) {
				if (!display1.readAndDispatch()) {
					display1.sleep();
